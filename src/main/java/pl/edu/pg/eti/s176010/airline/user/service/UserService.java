package pl.edu.pg.eti.s176010.airline.user.service;


import lombok.NoArgsConstructor;
import pl.edu.pg.eti.s176010.airline.controller.interceptor.binding.CatchEjbException;
import pl.edu.pg.eti.s176010.airline.file.FileUtility;
import pl.edu.pg.eti.s176010.airline.user.entity.Role;
import pl.edu.pg.eti.s176010.airline.user.entity.User;
import pl.edu.pg.eti.s176010.airline.user.repository.UserRepository;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for all business actions regarding user entity.
 */
@Stateless
@LocalBean
@NoArgsConstructor
@RolesAllowed({"USER", "ADMIN"})//Role.USER) TODO
public class UserService {

    /**
     * Repository for user entity.
     */
    private UserRepository repository;

    /**
     * Build in security context.
     */
    private SecurityContext securityContext;

    /**
     * Password hashing algorithm.
     */
    private Pbkdf2PasswordHash pbkdf;

    /**
     * @param repository repository for ticket entity
     */
    @Inject
    public UserService(UserRepository repository, SecurityContext securityContext, Pbkdf2PasswordHash pbkdf) {
        this.repository = repository;
        this.securityContext = securityContext;
        this.pbkdf = pbkdf;
    }

    /**
     * @param id existing id
     * @return container (can be empty) with user
     */
    public Optional<User> find(Long id) {
        return repository.find(id);
    }

    /**
     * @param email existing email
     * @return container (can be empty) with user
     */
    public Optional<User> find(String email) {
        return repository.findByEmail(email);
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
    @PermitAll
    public void create(User user) {
        if (!securityContext.isCallerInRole(Role.ADMIN.toString())) {
            user.setRoles(List.of(Role.USER));
        }
        user.setPassword(pbkdf.generate(user.getPassword().toCharArray()));
        repository.create(user);
    }


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

    /**
     * @return logged user entity
     */
    public Optional<User> findCallerPrincipal() {
        if (securityContext.getCallerPrincipal() != null) {
            return find(securityContext.getCallerPrincipal().getName());
        } else {
            return Optional.empty();
        }
    }


}
