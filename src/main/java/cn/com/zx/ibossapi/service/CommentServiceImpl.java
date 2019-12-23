package cn.com.zx.ibossapi.service;

import cn.com.zx.ibossapi.common.Constant;
import cn.com.zx.ibossapi.domain.*;
import cn.com.zx.ibossapi.mapper.iboss.CommentMapper;
import cn.com.zx.ibossapi.mapper.iboss.ExpertArticleMapper;
import cn.com.zx.ibossapi.util.DateUtil;
import cn.com.zx.ibossapi.util.ListUtil;
import cn.com.zx.ibossapi.util.LogUtil;
import cn.com.zx.ibossapi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lvxuezhan
 * @date 2019/11/27
 **/
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ExpertArticleMapper expertArticleMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public WxUserInfo getWxUserInfo(String openid) {
        return commentMapper.getWxUserInfo(openid);
    }

    @Override
    public int saveFirstLevelComment(WxUserInfo wxUserInfo, String eaId, String content) {
        int ret = 0;
        try{
            Query query=new Query(Criteria.where(Constant.ATLID).is(eaId));
            Document existsDodument = mongoTemplate.findOne(query,Document.class,Constant.ATLTBL);
            Comment comment = new Comment()
                    .setOpenid(wxUserInfo.getOpenid())
                    .setNikeName(wxUserInfo.getNickname())
                    .setHeadimgurl(wxUserInfo.getHeadimgurl())
                    .setContent(content)
                    .setTime(DateUtil.getMonthAndDay())
                    .setCmtNo(StringUtil.getRandomCmtNo())
                    .setApprNum(0)
                    .setAccuNum(0)
                    .setIsHidden(0)
                    .setReplyNum(0);
            if(existsDodument == null){
                Document document = new Document();
                document.setAtlId(eaId);
                List<Comment> comments = new ArrayList<>();
                comments.add(comment);
                document.setTotal(1);
                document.setComments(comments);
                mongoTemplate.insert(document,Constant.ATLTBL);
            }else{
                List<Comment> newComments = existsDodument.getComments();
                newComments.add(comment);
                Update update= new Update().set(Constant.COMMENTS, newComments).set("total",existsDodument.getTotal()+1);
                mongoTemplate.updateFirst(query,update,Constant.ATLTBL);
            }
            ret = 1;
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
        }
        return ret;
    }

    @Override
    public int saveSecondLevelComment(WxUserInfo wxUserInfo, String eaId, String content, String cmtNo) {
        int ret = 0;
        try{
            Query query=new Query(Criteria.where(Constant.ATLID).is(eaId));
            Document document = mongoTemplate.findOne(query,Document.class,Constant.ATLTBL);
            List<Comment> comments = document.getComments();
            Comment comment = comments.stream().filter(a->a.getCmtNo().equals(cmtNo)).findFirst().get();
            Comment newComment = new Comment()
                    .setOpenid(wxUserInfo.getOpenid())
                    .setNikeName(wxUserInfo.getNickname())
                    .setHeadimgurl(wxUserInfo.getHeadimgurl())
                    .setTime(DateUtil.getMonthAndDay())
                    .setContent(content)
                    .setApprNum(0)
                    .setAccuNum(0)
                    .setCmtNo(StringUtil.getRandomCmtNo())
                    .setIsHidden(0)
                    .setOppoNum(0);
            if(comment.getReplyNum() == 0){
                comment.setReplyNum(1);
                List<Comment> replyList = Arrays.asList(newComment);
                comment.setReplyList(replyList);
            }else{
                comment.setReplyNum(comment.getReplyNum()+1);
                comment.getReplyList().add(newComment);
            }
            Update update= new Update().set(Constant.COMMENTS, comments).set("total",document.getTotal()+1);
            mongoTemplate.updateFirst(query,update,Constant.ATLTBL);
            ret = 1;
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
        }
        return ret;
    }

    @Override
    public Document getCommentListByEaId(String eaId) {
        Document document = null;
        try{
            Query query=new Query(Criteria.where(Constant.ATLID).is(eaId));
            document = mongoTemplate.findOne(query,Document.class,Constant.ATLTBL);
            List<Comment> comments = document.getComments();
            List<Comment> firstShowList = comments.stream().filter(a->a.getIsHidden()==0).collect(Collectors.toList());
            if (!ListUtil.isBlank(firstShowList)){
                for(Comment comment : firstShowList){
                    List<Comment> replyList = comment.getReplyList();
                    if(!ListUtil.isBlank(replyList)){
                        List<Comment> secondShowList = replyList.stream().filter(a->a.getIsHidden()==0).collect(Collectors.toList());
                        comment.setReplyList(secondShowList);
                    }
                }
            }
            document.setComments(firstShowList);
        }catch(Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
        }
        return document;
    }

    @Override
    public int getCommentNum(String eaId) {
        int num = 0;
        try{
            Query query=new Query(Criteria.where(Constant.ATLID).is(eaId));
            Document document = mongoTemplate.findOne(query,Document.class,Constant.ATLTBL);
            if(document!=null){
                num = document.getTotal();
            }
        }catch(Exception e){
            e.printStackTrace();
            num = -1;
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
        }
        return num;
    }

    @Override
    public int firstLevelAppr(WxUserInfo wxUserInfo, String eaId, String cmtNo) {
        int ret = 0;
        try{
            Query oprQuery=new Query(Criteria.where(
                     Constant.ATLID).is(eaId)
                    .and(Constant.OPRTYPE).is(Constant.USERAPPR)
                    .and(Constant.COMMENTLEVEL).is(1)
                    .and(Constant.CMTNO).is(cmtNo)
                    .and(Constant.OPROPENID).is(wxUserInfo.getOpenid()));
            Opration existsOperation = mongoTemplate.findOne(oprQuery,Opration.class,Constant.OPRTBL);
            if(existsOperation != null){
                ret = 2;
                return ret;
            }
            Query query=new Query(Criteria.where(Constant.ATLID).is(eaId));
            Document document = mongoTemplate.findOne(query,Document.class,Constant.ATLTBL);
            List<Comment> comments = document.getComments();
            Comment coment = comments.stream().filter(a->a.getCmtNo().equals(cmtNo)).findFirst().get();
            coment.setApprNum(coment.getApprNum()+1);
            Update update= new Update().set(Constant.COMMENTS, comments);
            mongoTemplate.updateFirst(query,update,Constant.ATLTBL);

            //保存点赞记录
            Opration opration = new Opration()
                    .setAtlId(eaId)
                    .setCmtNo(cmtNo)
                    .setOprOpenid(wxUserInfo.getOpenid())
                    .setCommentLevel(1)
                    .setOprType(Constant.USERAPPR);
            mongoTemplate.insert(opration,Constant.OPRTBL);
            ret = 1;
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
        }
        return ret;
    }

    @Override
    public int secondLevelApprOppo(WxUserInfo wxUserInfo, String eaId, String cmtNo1, String cmtNo2, Integer oprType) {
        int ret = 0;
        try{
            Query oprQuery=new Query(Criteria.where(
                    Constant.ATLID).is(eaId)
                    .and(Constant.OPRTYPE).is(oprType)
                    .and(Constant.COMMENTLEVEL).is(2)
                    .and(Constant.OPROPENID).is(wxUserInfo.getOpenid())
                    .and(Constant.CMTNO).is(cmtNo2));
            Opration existsOperation = mongoTemplate.findOne(oprQuery,Opration.class,Constant.OPRTBL);
            if(existsOperation != null){
                //已经点赞或者点踩过
                ret = 2;
                return ret;
            }
            Query query=new Query(Criteria.where(Constant.ATLID).is(eaId));
            Document document = mongoTemplate.findOne(query,Document.class,Constant.ATLTBL);
            List<Comment> comments = document.getComments();
            Comment comment = comments.stream().filter(a->a.getCmtNo().equals(cmtNo1))
                    .findFirst().get().getReplyList().stream().
                    filter(a->a.getCmtNo().equals(cmtNo2)).findFirst().get();
            if(oprType.equals(Constant.USERAPPR)){
                comment.setApprNum(comment.getApprNum()+1);
            }else{
                comment.setOppoNum(comment.getOppoNum()+1);
            }
            Update update= new Update().set(Constant.COMMENTS, comments);
            mongoTemplate.updateFirst(query,update,Constant.ATLTBL);

            //保存操作记录
            Opration opration = new Opration()
                    .setAtlId(eaId)
                    .setOprOpenid(wxUserInfo.getOpenid())
                    .setCmtNo(cmtNo2)
                    .setCommentLevel(2)
                    .setOprType(oprType);
            mongoTemplate.insert(opration,Constant.OPRTBL);
            ret = 1;
        }catch(Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
        }
        return ret;
    }

    @Override
    public int accusation(WxUserInfo wxUserInfo, String eaId, String cmtNo1, String cmtNo2, int commentLevel, String reason) {
        int ret = 0;
        try{
            Criteria criteria = Criteria.where(Constant.ATLID).is(eaId).and("commentLevel").is(commentLevel).and("oprOpenid").is(wxUserInfo.getOpenid()).and("cmtNo1").is(cmtNo1);
            if(commentLevel == 2){
                criteria.and("cmtNo2").is(cmtNo2);
            }
            Accusation existsAccusation = mongoTemplate.findOne(new Query(criteria),Accusation.class,Constant.ACCUTBL);
            if(existsAccusation != null){
                //已经举报过
                ret = 2;
                return ret;
            }

            Query query=new Query(Criteria.where(Constant.ATLID).is(eaId));
            Document document = mongoTemplate.findOne(query,Document.class,Constant.ATLTBL);
            List<Comment> comments = document.getComments();
            Comment comment = null;
            if(commentLevel == 1){
                comment = comments.stream().filter(a->a.getCmtNo().equals(cmtNo1)).findFirst().get();
            }else{
                comment = comments.stream().filter(a->a.getCmtNo().equals(cmtNo1))
                        .findFirst().get().getReplyList().stream().
                        filter(a->a.getCmtNo().equals(cmtNo2)).findFirst().get();
            }

            if(comment.getAccuNum()==2){
                //屏蔽评论
                comment.setIsHidden(1);
            }
            comment.setAccuNum(comment.getAccuNum()+1);
            Update update= new Update().set(Constant.COMMENTS, comments);
            mongoTemplate.updateFirst(query,update,Constant.ATLTBL);

            //保存操作记录
            Accusation accusation = new Accusation()
                    .setAtlId(eaId)
                    .setCommentLevel(commentLevel)
                    .setAccuNo(StringUtil.getRandomAccuNo())
                    .setCmtNo1(cmtNo1)
                    .setCmtNo2(cmtNo2)
                    .setOprNikeName(wxUserInfo.getNickname())
                    .setBeOprNikeName(comment.getNikeName())
                    .setOprOpenid(wxUserInfo.getOpenid())
                    .setComment(comment.getContent())
                    .setReason(reason);
            mongoTemplate.insert(accusation,Constant.ACCUTBL);
            ret = 1;
        }catch(Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
        }
        return ret;
    }

    @Override
    public int collectComment(WxUserInfo wxUserInfo, String eaId, String cmtNo1) {
        int ret = 0;
        try{
            String eaTitle = expertArticleMapper.getEaTitleById(eaId);
            Query query=new Query(Criteria.where(Constant.ATLID).is(eaId));
            Document document = mongoTemplate.findOne(query,Document.class,Constant.ATLTBL);
            List<Comment> comments = document.getComments();
            Comment comment = comments.stream().filter(a->a.getCmtNo().equals(cmtNo1)).findFirst().get();
            CollectComment collectComment = new CollectComment()
                    .setEaId(eaId)
                    .setEaTitle(eaTitle)
                    .setOprOpenid(wxUserInfo.getOpenid())
                    .setCmtNo(cmtNo1)
                    .setComment(comment);
            Criteria criteria = Criteria.where("eaId").is(eaId).and("cmtNo").is(cmtNo1).and("oprOpenid").is(wxUserInfo.getOpenid());
            CollectComment existscollectComment = mongoTemplate.findOne(new Query(criteria),CollectComment.class,Constant.COLLECTTABLE);
            if(existscollectComment == null){
                mongoTemplate.insert(collectComment,Constant.COLLECTTABLE);
            }else{
                Update update= new Update().set("comment", comment);
                mongoTemplate.updateFirst(new Query(criteria),update,Constant.COLLECTTABLE);
            }
            ret =1;
        }catch(Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
        }
        return ret;
    }

    @Override
    public int removeComment(String openid, String cmtNo) {
        int ret = 0;
        try{
            Criteria criteria = Criteria.where("oprOpenid").is(openid).and("cmtNo").is(cmtNo);
            mongoTemplate.remove(new Query(criteria), CollectComment.class, Constant.COLLECTTABLE);
            ret = 1;
        }catch(Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
        }
        return ret;
    }

    @Override
    public List<CollectComment> getCollectComment(String openid) {
        List<CollectComment> collectCommentList = null;
        try{
            Query query=new Query(Criteria.where(Constant.OPROPENID).is(openid));
            collectCommentList = mongoTemplate.find(query,CollectComment.class,Constant.COLLECTTABLE);
        }catch(Exception e){
            e.printStackTrace();
            collectCommentList = new ArrayList<>();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
        }
        return collectCommentList;
    }

    @Override
    public List<Document> getAllComments() {
        return mongoTemplate.findAll(Document.class,Constant.ATLTBL);
    }

    @Override
    public List<Opration> getAllOpprs() {
        return mongoTemplate.findAll(Opration.class,Constant.OPRTBL);
    }

    @Override
    public List<CollectComment> getAllCollects() {
        return mongoTemplate.findAll(CollectComment.class,Constant.COLLECTTABLE);
    }

    @Override
    public List<Accusation> getAllAccus() {
        return mongoTemplate.findAll(Accusation.class,Constant.ACCUTBL);
    }

    @Override
    public Comment secondReplyList(String eaId, String cmtNo1) {
        try{
            Query query=new Query(Criteria.where(Constant.ATLID).is(eaId));
            Document document = mongoTemplate.findOne(query,Document.class,Constant.ATLTBL);
            List<Comment> comments = document.getComments();
            return comments.stream().filter(a->a.getCmtNo().equals(cmtNo1)).findFirst().get();
        }catch(Exception e){
            e.printStackTrace();
            LogUtil.getLogger(this.getClass()).error(e.getMessage());
        }
        return null;
    }

}
