package pl.edu.pg.eti.s176010.airline.controller.interceptor;


import pl.edu.pg.eti.s176010.airline.controller.interceptor.binding.CatchEjbException;

import javax.annotation.Priority;
import javax.ejb.EJBAccessException;
import javax.ejb.EJBTransactionRolledbackException;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

@Interceptor
@CatchEjbException
@Priority(10)
public class CatchEjbExceptionInterceptor {

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        try {
            return context.proceed();
        } catch (EJBAccessException ex) {
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .build();
        } catch (EJBTransactionRolledbackException ex){
            return Response
                    .status(Response.Status.CONFLICT)
                    .build();
        }
    }

}
