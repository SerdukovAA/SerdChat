package com.serd.chat.controllers;


        import com.serd.chat.util.GenereteHashUtil;
        import org.apache.commons.io.FilenameUtils;
        import java.io.BufferedOutputStream;
        import java.io.File;
        import java.io.FileOutputStream;

        import com.serd.chat.dao.UserDAO;
        import org.springframework.beans.factory.annotation.Autowired;

        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RequestParam;

        import org.springframework.web.multipart.MultipartFile;


        import javax.servlet.http.HttpServletRequest;


/**
 * Handles requests for the application file upload requests
 */
@Controller
public class FileUploadController  {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFileHandler(@RequestParam("user_id") int user_id,
                             @RequestParam("file") MultipartFile file, HttpServletRequest request) {





        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = request.getSession().getServletContext().getRealPath("/");
                File dir = new File(rootPath +"/WEB-INF/resource/avs/");
                if (!dir.exists())
                    dir.mkdirs();
                String ext = FilenameUtils.getExtension(file.getOriginalFilename());
                System.out.println("Расширение полученного файла - *."+ext);
                String newFileName = GenereteHashUtil.genHash(file.getOriginalFilename());
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()+"/"+newFileName+"."+ext);
                String avatarUrl = "/resource/avs/"+newFileName+"."+ext;
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                userDAO.setAvatar(user_id,avatarUrl);


                return "redirect:/chat_user_page";

            } catch (Exception e) {
                return "redirect:/chat_user_page";
            }
        } else {
            return "redirect:/chat_user_page";
        }
    }

}