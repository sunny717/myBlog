package com.ydemo.base.cores.utils;

import org.apache.commons.lang3.StringUtils;
import org.commonmark.Extension;  
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hongbi on 17/3/31.
 */
public class SystemUtils {
    public static synchronized String getUniqueOrderid() {
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR)).substring(2);
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        month = (month.length() <= 1) ? ("0" + month) : month;
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        day = (day.length() <= 1) ? ("0" + day) : day;
        String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        hour = (hour.length() <= 1) ? ("0" + hour) : hour;
        String minute = String.valueOf(cal.get(Calendar.MINUTE));
        minute = (minute.length() <= 1) ? ("0" + minute) : minute;
        String uniqueNumber = new StringBuffer(year).append(month).append(day).append(hour).append(minute)
                .append(String.valueOf(Math.random() * 3).substring(2, 5)).toString();

        return uniqueNumber;

    }

    /**
     * 获取内容中第一张图
     * @param content
     * @return
     */
    public static String get_first_thumb(String content) {
        content = markdownToHtml(content);
        if (content.contains("<img")) {
            String img = "";
            String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
            Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
            Matcher m_image = p_image.matcher(content);
            if (m_image.find()) {
                img = img + "," + m_image.group();
                // //匹配src
                Matcher m = Pattern.compile("src\\s*=\\s*\'?\"?(.*?)(\'|\"|>|\\s+)").matcher(img);
                if (m.find()) {
                    return m.group(1);
                }
            }
        }
        return "";
    }

    public static String markdownToHtml(String markdown){
        if(StringUtils.isEmpty(markdown)) return "";
        markdown = markdown.replace("<!--more-->", "\r\n");

        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        String content = renderer.render(document);

        // 支持网易云音乐输出
        if ( content.contains("[mp3:")) {
            content = content.replaceAll("\\[mp3:(\\d+)\\]", "<iframe frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" width=350 height=106 src=\"//music.163.com/outchain/player?type=2&id=$1&auto=0&height=88\"></iframe>");
        }
        // 支持gist代码输出
        if ( content.contains("https://gist.github.com/")) {
            content = content.replaceAll("&lt;script src=\"https://gist.github.com/(\\w+)/(\\w+)\\.js\">&lt;/script>", "<script src=\"https://gist.github.com/$1/$2\\.js\"></script>");
        }
        return content;
    }

    public static boolean isImage(MultipartFile imageFile)  {

        if (imageFile.isEmpty()) {
            return false;
        }
        try {
            Image img = ImageIO.read(imageFile.getInputStream());
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
