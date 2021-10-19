package pl.edu.pg.eti.s176010.airline.user.servlet;

import pl.edu.pg.eti.s176010.airline.file.FileUtility;
import pl.edu.pg.eti.s176010.airline.servlet.ServletUtility;
import pl.edu.pg.eti.s176010.airline.user.entity.User;
import pl.edu.pg.eti.s176010.airline.user.service.UserService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Optional;

/**
 * Servlet for serving and uploading tickets' avatars i raster image format.
 */
@WebServlet(urlPatterns = AvatarServlet.Paths.AVATARS + "/*")
@MultipartConfig(maxFileSize = 200 * 1024)
public class AvatarServlet extends HttpServlet {

    /**
     * Service for managing tickets.
     */
    private UserService service;

    @Inject
    public AvatarServlet(UserService service) {
        this.service = service;
    }

    /**
     * Definition of paths supported by this servlet. Separate inner class provides composition for static fields.
     */
    public static class Paths {

        /**
         * Specified avatar for download and upload.
         */
        public static final String AVATARS = "/api/avatars";

    }

    /**
     * Definition of regular expression patterns supported by this servlet. Separate inner class provides composition
     * for static fields. Whereas servlet activation path can be compared to {@link Paths} the path info (denoted by
     * wildcard in paths) can be compared using regular expressions.
     */
    public static class Patterns {

        /**
         * Specified avatar (for download).
         */
        public static final String USER_WITH_AVATAR = "^/[0-9]+/?$";

    }

    /**
     * Request parameters (both query params and request parts) which can be sent by the client.
     */
    public static class Parameters {

        /**
         * avatar image part.
         */
        public static final String AVATAR = "avatar";

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.USER_WITH_AVATAR)) {
                getAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.USER_WITH_AVATAR)) {
                putAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.USER_WITH_AVATAR)) {
                postAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void postAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = service.find(id);

        if (user.isPresent()) {
            if(user.get().getAvatarFileName()!=null)
            {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                return;
            }
            Part avatar = request.getPart(Parameters.AVATAR);
            if (avatar != null) {
                String dirPath = getServletContext().getInitParameter("storedFilePath");
                service.createAvatar(id, avatar.getInputStream(), dirPath);
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            }
            else
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.USER_WITH_AVATAR)) {
                deleteAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void deleteAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = service.find(id);

        if (user.isPresent() && user.get().getAvatarFileName()!=null) {
            String dirPath = getServletContext().getInitParameter("storedFilePath");
            service.deleteAvatar(id, dirPath);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Updates ticket's avatar. Receives avatar bytes from request and stores them in the data storage.
     *
     * @param request  http request
     * @param response http response
     * @throws IOException      if any input or output exception occurred
     * @throws ServletException if this request is not of type multipart/form-data
     */
    private void putAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = service.find(id);

        if (user.isPresent() && user.get().getAvatarFileName()!=null) {
            Part avatar = request.getPart(Parameters.AVATAR);
            if (avatar != null) {
                String dirPath = getServletContext().getInitParameter("storedFilePath");
                service.updateAvatar(id, avatar.getInputStream(), dirPath);
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            }
            else
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Fetches avatar as byte array from data storage and sends is through http protocol.
     *
     * @param request  http request
     * @param response http response
     * @throws IOException if any input or output exception occurred
     */
    private void getAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(ServletUtility.parseRequestPath(request).replaceAll("/", ""));
        Optional<User> user = service.find(id);

        if (user.isPresent() && user.get().getAvatarFileName()!=null) {
            String dirPath = getServletContext().getInitParameter("storedFilePath");
            byte[] avatar = FileUtility.getBytes(dirPath, user.get().getAvatarFileName());
            //Type should be stored in the database but in this project we assume everything to be png.
            response.addHeader("Content-Type", "image/png");
            response.setContentLength(avatar.length);
            response.getOutputStream().write(avatar);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
