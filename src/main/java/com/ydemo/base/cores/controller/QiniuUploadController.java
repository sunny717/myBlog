package com.ydemo.base.cores.controller;

import com.ydemo.base.cores.obj.AjaxResult;
import com.ydemo.base.cores.utils.QIniuUtils;
import com.ydemo.base.cores.utils.QiniuBucketType;
import com.ydemo.base.cores.utils.SystemUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 七牛上传接口
 *
 */
@Controller
@RequestMapping("/qiniu/upload")
public class QiniuUploadController {


    /**
     * 使用weboploader组件的时候 文件名是file
     * 使用kindeditor的时候 文件名师 imgFile
     * */

    @RequestMapping(value="/image",method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult uploadImage(@RequestParam("file[0]") MultipartFile imgFile) {
        if (imgFile == null || imgFile.isEmpty()) {
            return new AjaxResult(false, "请选择图片");
        }
        try {

            String ftype = SystemUtils.isImage(imgFile)?"image":"file";
            QIniuUtils.MyRet ret = QIniuUtils.upImageFile(imgFile.getBytes(), QiniuBucketType.IMAGE);
            ret.filetype = ftype;
            return new AjaxResult(true,"success", ret);
        } catch (IOException e) {
            return new AjaxResult(false, "上传失败");
        }
    }
}
