package com.ensias.moroccan_cars.services;

import com.ensias.moroccan_cars.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {
    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;
    public String app_folder = "moroccan_cars";
    @Autowired
    ResourceLoader resourceLoader;

    public void store(MultipartFile file,String name) throws IOException {

        Files.createDirectories(Paths.get(uploadDir + File.separator +app_folder));
        try {
            Path copyLocation = Paths.get(uploadDir + File.separator +app_folder
                    + File.separator + StringUtils.cleanPath(name));

            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("file : "+file.getOriginalFilename() + " stored in Path : " + copyLocation );
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }
    }

    public Resource loadResource(int id){
        Resource resource =   resourceLoader.getResource("file:" + Paths.get(uploadDir + File.separator +app_folder
                + File.separator + StringUtils.cleanPath(id+"")));

        return resource ;
    }
}


