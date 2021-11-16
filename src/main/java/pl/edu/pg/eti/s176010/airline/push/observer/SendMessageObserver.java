package pl.edu.pg.eti.s176010.airline.push.observer;

import lombok.extern.java.Log;
import pl.edu.pg.eti.s176010.airline.push.context.PushMessageContext;
import pl.edu.pg.eti.s176010.airline.push.dto.DirectMessage;
import pl.edu.pg.eti.s176010.airline.push.dto.Message;
import pl.edu.pg.eti.s176010.airline.push.observer.event.SendDirectMessageEvent;
import pl.edu.pg.eti.s176010.airline.push.observer.event.SendMessageEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;

/**
 * Observer implementation for deleting ticket.
 */
@ApplicationScoped
@Log
public class SendMessageObserver {

    /**
     * Build in security context.
     */
    private SecurityContext securityContext;

    /**
     * Context for sending push messages.
     */
    private PushMessageContext pushMessageContext;

    @Inject
    public void setPushMessageContext(PushMessageContext pushMessageContext) {
        this.pushMessageContext = pushMessageContext;
    }

    @Inject
    public void setSecurityContext(SecurityContext securityContext) {

        this.securityContext = securityContext;
    }


    public void processSendDirectMessage(@Observes @SendDirectMessageEvent DirectMessage message) {
        pushMessageContext.notifyUser(Message.builder()
                .from(message.getFrom())
                .content(message.getContent())
                .build(), message.getTo());
    }

    public void processSendMessage(@Observes @SendMessageEvent Message message) {
        pushMessageContext.notifyAll(Message.builder()
                .from(message.getFrom())
                .content(message.getContent())
                .build());
    }

}
