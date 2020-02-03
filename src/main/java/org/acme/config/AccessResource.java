package org.acme.config;

import org.acme.models.Acl;
import org.acme.models.RbacResponse;

import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/access")
@Singleton
public class AccessResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RbacResponse getUserAccess(
            @QueryParam("application") String applicationName,
            @HeaderParam("x-rh-identity") String xRHIdentity
    ) {
        if (applicationName == null) {
            throw new BadRequestException("You need to send the application queryParam");
        }
        if (xRHIdentity == null) {
            throw new BadRequestException("You need to send the x-rh-identity header");
        }

        List<Acl> acls = new ArrayList<>();
        acls.add(
                new Acl("migration-analytics" + ":*:*", Collections.emptyList())
        );

        RbacResponse.Meta meta = new RbacResponse.Meta(1, 10, 0);
        RbacResponse.Links links = new RbacResponse.Links(null, null, null, null);
        return new RbacResponse(meta, links, acls);
    }
}
