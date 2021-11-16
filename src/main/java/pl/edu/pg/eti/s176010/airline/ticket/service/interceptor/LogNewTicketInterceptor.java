package pl.edu.pg.eti.s176010.airline.ticket.service.interceptor;

import lombok.extern.java.Log;
import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;
import pl.edu.pg.eti.s176010.airline.ticket.service.interceptor.binding.LogNewTicket;
import pl.edu.pg.eti.s176010.airline.user.entity.Role;

import javax.annotation.Priority;
import javax.ejb.EJBAccessException;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;

@Interceptor
@LogNewTicket
@Priority(12)
@Log
public class LogNewTicketInterceptor {

    /**
     * Security context.
     */
    private SecurityContext securityContext;

    private TicketService ticketService;

    @Inject
    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @Inject
    public void setCharacterService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        //before invoke
        Object result = context.proceed();
        log(context);
        //after invoke
        return result;

    }

    private void log(InvocationContext context) {
        Object provided = context.getParameters()[0];
        Ticket original = provided instanceof Ticket ?
                ticketService.find(((Ticket) provided).getId()).orElseThrow() :
                ticketService.find((Long) provided).orElseThrow();
        log.info(securityContext.getCallerPrincipal().getName() + " added ticket " + original.getId());
    }

}
