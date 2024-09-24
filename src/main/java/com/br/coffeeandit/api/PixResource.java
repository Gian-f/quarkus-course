package com.br.coffeeandit.api;

import com.br.coffeeandit.model.LinhaDigitavel;
import com.br.coffeeandit.model.Pix;
import com.br.coffeeandit.model.Transaction;
import com.br.coffeeandit.service.DictService;
import com.br.coffeeandit.service.PixService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Path("/v1/pix")
public class PixResource {

    @Inject
    DictService dictService;

    @Inject
    PixService pixService;

    public static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Operation(description = "API para criar uma linha digitável")
    @APIResponseSchema(LinhaDigitavel.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "201", description = "Retorno OK com a linha criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado")
    })
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

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    @GET
    @Operation(description = "API responsável por aprovar um pagamento PIX")
    @APIResponseSchema(Transaction.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "201", description = "Retorno OK com a transação criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    public Response buscarPix(@PathParam("uuid") String uuid) {
        return Response.ok(pixService.findById(uuid)).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/transacoes")
    @GET
    @Operation(description = "API responsável por buscar pagamentos PIX")
    @APIResponseSchema(Transaction.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "201", description = "Retorno OK com a transação criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @Parameter(
            name = "dataInicio",
            in = ParameterIn.QUERY,
            description = "Data de Inicio no formato yyyy-MM-dd"
    )
    @Parameter(
            name = "dataFim",
            in = ParameterIn.QUERY,
            description = "Data do Fim no formato yyyy-MM-dd"
    )
    public Response buscarTransacoes(@QueryParam("dataInicio") String dataInicio, @QueryParam("dataFim") String dataFim) throws ParseException {
        return Response.ok(pixService.buscarTransacoes(SIMPLE_DATE_FORMAT.parse(dataInicio), SIMPLE_DATE_FORMAT.parse(dataFim))).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/aprovar/{uuid}")
    @PATCH
    @Operation(description = "API responsável por aprovar um pagamento PIX")
    @APIResponseSchema(Transaction.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "201", description = "Retorno OK com a transação criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    public Response aprovarPix(@PathParam("uuid") String uuid) {
        return Response.ok(pixService.aprovarTransacao(uuid).get()).build();
    }

    @Operation(description = "API para buscar um QRCode a partir de um UUID específico.")
    @APIResponseSchema(Response.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "201", description = "Retorno OK com a transação criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("image/png")
    @Path("/{uuid}/qrcode")
    public Response qrCode(@PathParam("uuid") String uuid) throws IOException {
        // TODO Adicionar controle de exceções
        return Response.ok(pixService.gerarQrCode(uuid)).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reprovar/{uuid}")
    @DELETE
    @Operation(description = "API responsável por reprovar um pagamento PIX")
    @APIResponseSchema(Transaction.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "201", description = "Retorno OK com a transação criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    public Response reprovarPix(@PathParam("uuid") String uuid) {
        return Response.ok(pixService.reprovarTransacao(uuid).get()).build();
    }
}