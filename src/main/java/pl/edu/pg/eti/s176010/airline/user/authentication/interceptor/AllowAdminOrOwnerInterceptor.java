package pl.edu.pg.eti.s176010.airline.user.authentication.interceptor;

import pl.edu.pg.eti.s176010.airline.ticket.entity.Ticket;
import pl.edu.pg.eti.s176010.airline.ticket.service.TicketService;
import pl.edu.pg.eti.s176010.airline.user.authentication.interceptor.binding.AllowAdminOrOwner;
import pl.edu.pg.eti.s176010.airline.user.entity.Role;

import javax.annotation.Priority;
import javax.ejb.EJBAccessException;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;

@Interceptor
@AllowAdminOrOwner
@Priority(10)
public class AllowAdminOrOwnerInterceptor {

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
        if (authorized(context)) {
            return context.proceed();
        } else {
            throw new EJBAccessException("Authorization failed for user " + securityContext.getCallerPrincipal().getName());

        }
    }

    private boolean authorized(InvocationContext context) {
        if (securityContext.isCallerInRole(Role.ADMIN.name())) {
            return true;
        } else if (securityContext.isCallerInRole(Role.USER.name())) {
            Object provided = context.getParameters()[0];
            Ticket original = provided instanceof Ticket ?
                    ticketService.find(((Ticket) provided).getId()).orElseThrow() :
                    ticketService.find((Long) provided).orElseThrow();
            return securityContext.getCallerPrincipal().getName().equals(original.getUser().getEmail());
        }
        return false;
    }

}
