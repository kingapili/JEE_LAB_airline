package pl.edu.pg.eti.s176010.airline.user.service;


import lombok.NoArgsConstructor;
import pl.edu.pg.eti.s176010.airline.file.FileUtility;
import pl.edu.pg.eti.s176010.airline.user.entity.User;
import pl.edu.pg.eti.s176010.airline.user.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding user entity.
 */
@ApplicationScoped
@NoArgsConstructor
public class UserService {

    /**
     * Repository for user entity.
     */
    private UserRepository repository;

    /**
     * @param repository repository for ticket entity
     */
    @Inject
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    /**
     * @param id existing username
     * @return container (can be empty) with user
     */
    public Optional<User> find(Long id) {
        return repository.find(id);
    }

    /**
     *  @return list (can be empty) with users
     */
    public List<User> findAll() {
        return repository.findAll();
    }

    /**
     * Saves new user.
     *
     * @param user new user to be saved
     */
    @Transactional
    public void create(User user) {
        repository.create(user);
    }


    @Transactional
    public void updateAvatar(Long id, InputStream inputStream, String dirPath) {
        repository.find(id).ifPresent(user -> {
            try {
                var avatarFileName = user.getAvatarFileName();
                FileUtility.deleteFile(dirPath, avatarFileName);
                String fileName = id + ".png";
                FileUtility.saveFile(dirPath,fileName,inputStream.readAllBytes());
                user.setAvatarFileName(fileName);
                //repository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    @Transactional
    public void createAvatar(Long id, InputStream inputStream, String dirPath) {
        repository.find(id).ifPresent(user -> {
            try {
                String fileName = id + ".png";
                FileUtility.saveFile(dirPath,fileName,inputStream.readAllBytes());
                user.setAvatarFileName(fileName);
                //repository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    @Transactional
    public void deleteAvatar(Long id, String dirPath) {
        repository.find(id).ifPresent(user -> {
            try {
                var avatarFileName = user.getAvatarFileName();
                FileUtility.deleteFile(dirPath, avatarFileName);
                user.setAvatarFileName(null);
                //repository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }


}
