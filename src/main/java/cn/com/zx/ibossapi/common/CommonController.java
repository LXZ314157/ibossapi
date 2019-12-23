package cn.com.zx.ibossapi.common;

import cn.com.zx.ibossapi.config.WxConfig;
import cn.com.zx.ibossapi.result.Result;
import cn.com.zx.ibossapi.result.ResultCodeMessage;
import cn.com.zx.ibossapi.result.ResultGenerator;
import cn.com.zx.ibossapi.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/common")
public class CommonController {
    private static final Pattern RANGE_PATTERN = Pattern.compile("");

    @Autowired
    private CommonService commonService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private WxConfig wxConfig;

    /**
     * 预览图片
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/getImage")
    @ResponseBody
    public void getImage(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String path = request.getParameter("path");
        FileInputStream inputStream = new FileInputStream(path);
        int i = inputStream.available();
        byte[] buff = new byte[i];
        inputStream.read(buff);
        inputStream.close();
        response.setContentType("image/*");
        OutputStream out = response.getOutputStream();
        out.write(buff);
        out.close();
    }


     /** 显示视频文件
     * @param response
     * @throws Exception
     */
    @GetMapping("/getVedio")
    public void testVedio(HttpServletRequest request,HttpServletResponse response, @RequestParam String path) throws Exception{
        String fileName = path.substring(path.lastIndexOf("/")+1);
        File file = new File(path);
        RandomAccessFile randomFile = new RandomAccessFile(file, "r");//只读模式
        long contentLength = randomFile.length();
        String range = request.getHeader("Range");
        int start = 0, end = 0;
        if(range != null && range.startsWith("bytes=")){
            String[] values = range.split("=")[1].split("-");
            start = Integer.parseInt(values[0]);
            if(values.length > 1){
                end = Integer.parseInt(values[1]);
            }
        }
        int requestSize = 0;
        if(end != 0 && end > start){
            requestSize = end - start + 1;
        } else {
            requestSize = Integer.MAX_VALUE;
        }

        byte[] buffer = new byte[4096];
        response.setContentType("video/mp4");
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("ETag", fileName);
        response.setHeader("Last-Modified", new Date().toString());
        //第一次请求只返回content length来让客户端请求多次实际数据
        if(range == null){
            response.setHeader("Content-length", contentLength + "");
        }else{
            //以后的多次以断点续传的方式来返回视频数据
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);//206
            long requestStart = 0, requestEnd = 0;
            String[] ranges = range.split("=");
            if(ranges.length > 1){
                String[] rangeDatas = ranges[1].split("-");
                requestStart = Integer.parseInt(rangeDatas[0]);
                if(rangeDatas.length > 1){
                    requestEnd = Integer.parseInt(rangeDatas[1]);
                }
            }
            long length = 0;
            if(requestEnd > 0){
                length = requestEnd - requestStart + 1;
                response.setHeader("Content-length", "" + length);
                response.setHeader("Content-Range", "bytes " + requestStart + "-" + requestEnd + "/" + contentLength);
            }else{
                length = contentLength - requestStart;
                response.setHeader("Content-length", "" + length);
                response.setHeader("Content-Range", "bytes "+ requestStart + "-" + (contentLength - 1) + "/" + contentLength);
            }
        }
        ServletOutputStream out = response.getOutputStream();
        int needSize = requestSize;
        randomFile.seek(start);
        while(needSize > 0){
            int len = randomFile.read(buffer);
            if(needSize < buffer.length){
                out.write(buffer, 0, needSize);
            } else {
                out.write(buffer, 0, len);
                if(len < buffer.length){
                    break;
                }
            }
            needSize -= buffer.length;
        }
        randomFile.close();
        out.close();

    }
    //获取首页机构标签列表
    @GetMapping("/orgTgList")
    @ResponseBody
    public Result getOrgTgList(){
        List<Map<String,String>> orgTgList = commonService.getOrgTgList();
        if(orgTgList!=null){
            return ResultGenerator.genSuccessResult(orgTgList);
        }else{
            return ResultGenerator.genFailResult(ResultCodeMessage.SYSTEM_EXCEPTION.getMessage());
        }
    }

}
