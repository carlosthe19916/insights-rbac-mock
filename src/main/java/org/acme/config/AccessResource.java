package org.acme.config;

import org.acme.models.Acl;
import org.acme.models.RbacResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/access")
@Singleton
public class AccessResource {

    @ConfigProperty(name = "rbac.permissions")
    String permissions;

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

        List<Acl> acls = Stream.of(permissions.split(","))
                .map(String::trim)
                .map(f -> new Acl(f, Collections.emptyList()))
                .collect(Collectors.toList());

        RbacResponse.Meta meta = new RbacResponse.Meta(1, 10, 0);
        RbacResponse.Links links = new RbacResponse.Links(null, null, null, null);
        return new RbacResponse(meta, links, acls);
    }
}
