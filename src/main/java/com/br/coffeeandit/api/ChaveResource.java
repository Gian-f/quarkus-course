package com.br.coffeeandit.api;

import com.br.coffeeandit.model.Chave;
import com.br.coffeeandit.service.DictService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/v1/chaves")
public class ChaveResource {

    @Inject
    DictService dictService;


    @Operation(description = "Endpoint para buscar chaves pix")
    @APIResponseSchema(Chave.class)
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "OK"
            ),
            @APIResponse(
                    responseCode = "401",
                    description = "Erro de autenticação"
            ),
            @APIResponse(
                    responseCode = "403",
                    description = "Erro de autorização"
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Recurso não encontrado"
            )
    }
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{chave}")
    public Response buscar(@PathParam("chave") String chave) {
        Chave chaveCached = dictService.buscarDetalhesChave(chave);
        return Response.ok(chaveCached).build();
    }
}
