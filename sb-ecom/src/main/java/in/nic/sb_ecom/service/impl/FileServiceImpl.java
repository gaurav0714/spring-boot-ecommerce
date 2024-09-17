package in.nic.sb_ecom.service.impl;

import in.nic.sb_ecom.service.FileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.separator + fileName;

        File folder = new File(filePath);
        if(!folder.exists()) {
            folder.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }
}
