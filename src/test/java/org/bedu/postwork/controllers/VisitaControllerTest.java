package org.bedu.postwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.postwork.model.Cliente;
import org.bedu.postwork.model.Visita;
import org.bedu.postwork.services.VisitaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs
@WebMvcTest(VisitaController.class)
public class VisitaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitaService visitaService;


    @Test
    void getVisita() throws Exception {
        given(visitaService.obtenVisita(anyLong())).willReturn(Optional.of(Visita.builder().id(1L).direccion("Av siempreviva").proposito("mostrar nuevos productos").build()));

        mockMvc.perform(get("/visita/{visitaid}",1)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.direccion", is("Av siempreviva")))
                .andExpect(jsonPath("$.proposito", is("mostrar nuevos productos")))

                .andDo(document("visita/get-visita",
                        pathParameters(
                                parameterWithName("visitaid").description("Identificador de la visita")
                        ),
                        responseFields(
                                fieldWithPath("id").description("identificador del cliente"),
                                fieldWithPath("cliente").description("nombre del cliente"),
                                fieldWithPath("fechaProgramada").description("fecha acordada para la visita"),
                                fieldWithPath("direccion").description("direccion del cliente"),
                                fieldWithPath("proposito").description("proposito de la visita"),
                                fieldWithPath("vendedor").description("nombre del vendedor")
                        )));
    }

    @Test
    void getVisitas() throws Exception {

        List<Visita> visitas = Arrays.asList(
                Visita.builder().id(1L).direccion("Direccion 1").proposito("proposito").vendedor("vendedor 1").build(),
                Visita.builder().id(2L).direccion("Direccion 2").proposito("proposito").vendedor("vendedor 2").build(),
                Visita.builder().id(3L).direccion("Direccion 3").proposito("proposito").vendedor("vendedor 3").build()
        );

        given(visitaService.obtenVisitas()).willReturn(visitas);

        mockMvc.perform(get("/visita")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[0].direccion", is("Direccion 1")))
                .andExpect(jsonPath("$[2].proposito", is("proposito")))

                .andDo(document("visita/get-visitas",
                        responseFields(
                                fieldWithPath("[].id").description("identificador de la visita"),
                                fieldWithPath("[].cliente").description("nombre del cliente a visitar"),
                                fieldWithPath("[].fechaProgramada").description("fecha acordada para la visita"),
                                fieldWithPath("[].direccion").description("direccion de la visita"),
                                fieldWithPath("[].proposito").description("proposito de la visita"),
                                fieldWithPath("[].vendedor").description("vendedor que realizara la visita")
                        )));
    }

    @Test
    void creaVisita() throws Exception {
        Visita visitaParametro = Visita.builder().direccion("Direccion 1").proposito("proposito").vendedor("vendedor 1").build();
        Visita visitaRespuesta = Visita.builder().id(1L).direccion("Direccion 1").proposito("proposito").vendedor("vendedor 1").build();

        given(visitaService.guardaVisita(visitaParametro)).willReturn(visitaRespuesta);

        mockMvc.perform(post("/visita")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(visitaParametro)))
                .andExpect(status().isCreated())
                .andDo(document("visita/post-visita",
                        requestFields(
                                fieldWithPath("id").description("El identificador de la nueva visita"),
                                fieldWithPath("cliente").description("El nombre del cliente"),
                                fieldWithPath("fechaProgramada").description("fecha acordada para la visita"),
                                fieldWithPath("direccion").description("direccion del cliente"),
                                fieldWithPath("proposito").description("proposito de la visita"),
                                fieldWithPath("vendedor").description("vendedor que realizara la visita")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("La ubicación del recurso (su identificador generado")
                        ))
                );
    }

    @Test
    void actualizaVisita() throws Exception {

        Visita visitaParametro = Visita.builder().direccion("Direccion 1").proposito("proposito").vendedor("vendedor 1").build();
        mockMvc.perform(put("/visita/{visitaid}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(visitaParametro)))
                .andExpect(status().isNoContent())
                .andDo(document("visita/put-visita",
                        pathParameters(
                                parameterWithName("visitaid").description("Identificador de la visita")
                        ),
                        requestFields(
                                fieldWithPath("id").description("El identificador de la visita"),
                                fieldWithPath("cliente").description("El nombre del cliente"),
                                fieldWithPath("fechaProgramada").description("fecha acordada para la visita"),
                                fieldWithPath("direccion").description("direccion del cliente"),
                                fieldWithPath("proposito").description("proposito de la visita"),
                                fieldWithPath("vendedor").description("vendedor que realizara la visita")
                        )
                ));
    }

    @Test
    void eliminaVisita() throws Exception {
        mockMvc.perform(delete("/visita/{visitaid}", 1)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent())
                .andDo(document("visita/delete-visita",
                        pathParameters(
                                parameterWithName("visitaid").description("Identificador de la visita")
                        )));
    }


}
