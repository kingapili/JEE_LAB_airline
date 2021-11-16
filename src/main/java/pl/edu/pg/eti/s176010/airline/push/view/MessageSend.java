package pl.edu.pg.eti.s176010.airline.push.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.s176010.airline.push.dto.DirectMessage;
import pl.edu.pg.eti.s176010.airline.push.dto.Message;
import pl.edu.pg.eti.s176010.airline.push.observer.event.SendDirectMessageEvent;
import pl.edu.pg.eti.s176010.airline.push.observer.event.SendMessageEvent;
import pl.edu.pg.eti.s176010.airline.user.entity.User;
import pl.edu.pg.eti.s176010.airline.user.model.UserModel;
import pl.edu.pg.eti.s176010.airline.user.service.UserService;

import javax.ejb.EJB;
import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.swing.*;
import java.io.Console;
import java.io.Serializable;
import java.util.Optional;

@ViewScoped
@Named
public class MessageSend implements Serializable {
    /**
     * Service for managing users.
     */
    private UserService userService;

    private  Event<Message> messageEvent;

    private  Event<DirectMessage> directMessageEvent;

    SecurityContext securityContext;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private String directContent;

    @Getter
    @Setter
    private UserModel reciever;

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setSendMessageEvent(@SendMessageEvent Event<Message> messageEvent)
    {
        this.messageEvent = messageEvent;
    }

    @Inject
    public void setSendDirectMessageEvent(@SendDirectMessageEvent Event<DirectMessage> directMessageEvent)
    {
        this.directMessageEvent = directMessageEvent;
    }

    @Inject
    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    /**
     * Action for clicking send action.
     *
     */
    public void sendAction() {
        Message message = Message.builder()
                .from(securityContext.getCallerPrincipal().getName())
                .content(content).build();
        messageEvent.fire(message);
    }

    /**
     * Action for clicking send action.
     *
     */
    public void sendDirectAction() {
        DirectMessage message = DirectMessage.builder()
            .from(securityContext.getCallerPrincipal().getName())
            .content(directContent).to(reciever.getEmail()).build();
        directMessageEvent.fire(message);
    }
}
