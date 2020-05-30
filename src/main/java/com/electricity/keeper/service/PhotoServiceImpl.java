package com.electricity.keeper.service;

import com.electricity.keeper.model.Photo;
import com.electricity.keeper.repository.HistoryRepository;
import com.electricity.keeper.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;


@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    ResourceLoader resourceLoader;

    @Value("${images.path}")
    private String imagesPath;

    @Override
    public InputStream getImage(long id) throws IOException {
        var photo = photoRepository.findById(id);
        if(photo.isEmpty()) {
            throw new IOException("There isn't exist image with id: " + id);
        }

        var username = userService.getCurrentUsername();
        var images = new File(imagesPath);
        var userFolder = new File(images, username);

        if(userFolder.exists() && userFolder.isDirectory()) {
            var files = userFolder.listFiles();
            if(files != null) {
                var fileName = photo.get().getName();

                var file = Arrays.stream(files)
                        .filter(f -> f.getName().contains(fileName))
                        .findFirst();

                if(file.isPresent()) {
                    return new FileInputStream(file.get());
                } else {
                    throw new IOException("There isn't exist image with id: " + fileName);
                }
            } else {
                throw new IOException("User folder are empty");
            }
        } else {
            throw new IOException("User folder are not exist");
        }
    }

    @Override
    public void saveImage(InputStream in, long historyId) throws IOException {
        if(in.available() == 0) {
            throw new IOException("No bytes of photo");
        }

        var historyResult = historyRepository.findById(historyId);
        if(historyResult.isEmpty()) {
            throw new IOException("No history with id: " + historyId);
        }

        var history = historyResult.get();

        var username = userService.getCurrentUsername();
        var images = new File(imagesPath);
        var userFolder = new File(images, username);

        Photo newPhoto = new Photo();
        newPhoto.setHistory(history);
        newPhoto.setName(String.valueOf(System.currentTimeMillis()));
        history.setPhoto(newPhoto);

        if(!userFolder.exists() || !userFolder.isDirectory()) {
            userFolder.mkdir();
        }

        File photoFile = new File(userFolder, newPhoto.getName());

        if(!photoFile.createNewFile()) {
            newPhoto.setName(String.valueOf(System.currentTimeMillis()));
            var anotherFile = new File(userFolder, newPhoto.getName());
            photoFile.renameTo(anotherFile);
        }

        Files.write(Paths.get(photoFile.getAbsolutePath()), in.readAllBytes());
        photoRepository.save(newPhoto);
    }

    @Override
    public void changeImage(InputStream in, long photoId) throws IOException {
        if(in.available() == 0) {
            throw new IOException("No bytes of photo");
        }

        var photoOptional = photoRepository.findById(photoId);

        if(photoOptional.isEmpty()) {
            throw new IOException("No photo with id: " + photoId);
        }

        var photo = photoOptional.get();
        var username = userService.getCurrentUsername();
        var images = new File(imagesPath);
        var userFolder = new File(images, username);

        File existPhoto = new File(userFolder, photo.getName());
        Files.write(Paths.get(existPhoto.getAbsolutePath()), in.readAllBytes());
    }

    @Override
    public void removeImage(long photoId) throws IOException {
        var photoOptional = photoRepository.findById(photoId);

        if(photoOptional.isEmpty()) {
            throw new IOException("No photo with id: " + photoId);
        }

        var photo = photoOptional.get();
        var username = userService.getCurrentUsername();
        var images = new File(imagesPath);
        var userFolder = new File(images, username);
        File existPhoto = new File(userFolder, photo.getName());
        if(!existPhoto.delete()) {
            throw new IOException("Can't remove photo. Internal system error");
        }

        photo.setName(null);
        photoRepository.save(photo);
    }
}
