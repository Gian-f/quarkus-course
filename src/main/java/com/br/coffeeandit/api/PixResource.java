package com.br.coffeeandit.api;

import com.br.coffeeandit.model.LinhaDigitavel;
import com.br.coffeeandit.model.Pix;
import com.br.coffeeandit.service.DictService;
import com.br.coffeeandit.service.PixService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.io.IOException;
import java.util.Objects;

@Path("/v1/pix")
public class PixResource {

    @Inject
    DictService dictService;

    @Inject
    PixService pixService;


    @Operation(description = "API para criar uma linha digitável")
    @APIResponseSchema(LinhaDigitavel.class)
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "OK"
            ),
            @APIResponse(
                    responseCode = "201",
                    description = "retorno OK com a linha digitável criada."
            ),
            @APIResponse(
                    responseCode = "401",
                    description = "Erro de autenticação dessa API"
            ),
            @APIResponse(
                    responseCode = "403",
                    description = "Erro de autenticação dessa API."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Recurso não encontrado"
            )
    }
    )

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/linha")
    public Response gerarLinhaDigitavel(final Pix pix) {
        var chave = dictService.buscarChave(pix.chave());

        if (Objects.nonNull(chave)) {
            return Response.ok(pixService.gerarLinhaDigitavel(chave, pix.valor(), pix.cidadeRemetente())).build();
        }
        return null;
    }


    @Operation(description = "Endpoint para buscar um QRCode a partir de um UUID específico.")
    @APIResponseSchema(Response.class)
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "OK"
            ),
            @APIResponse(
                    responseCode = "201",
                    description = "retorno OK com a transação criada."
            ),
            @APIResponse(
                    responseCode = "401",
                    description = "Erro de autenticação dessa API"
            ),
            @APIResponse(
                    responseCode = "403",
                    description = "Erro de autenticação dessa API."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Recurso não encontrado"
            )
    }
    )
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("image/png")
    @Path("/qrcode/{uuid}")
    public Response qrCode(@PathParam("uuid") String uuid) throws IOException {
        // TODO implementar controle de exceções
        return Response.ok(pixService.gerarQrCode(uuid)).build();
    }
}
