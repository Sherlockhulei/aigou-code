package cn.itsource.common.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.FastDfsApiOpr;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FastDFSController {

    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file){
        String filename = file.getOriginalFilename();
        filename.substring(filename.lastIndexOf(".")+1);
        try {
            String upload = FastDfsApiOpr.upload(file.getBytes(), filename);
            return AjaxResult.getAjaxResult().setMsg("上传成功").setResultObj(upload);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.getAjaxResult().setSuccess(false).setMsg("上传失败"+e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public AjaxResult delete(String fildId){
        try {
            fildId = fildId.substring(1);
            int index = fildId.lastIndexOf(".");
            String groupName = fildId.substring(0, index);
            String fileName = fildId.substring(index+1 );
            FastDfsApiOpr.delete(groupName,fileName );
            return AjaxResult.getAjaxResult().setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.getAjaxResult().setSuccess(false).setMsg("删除失败"+e.getMessage());
        }
    }

}
