package org.bedu.postwork.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.postwork.model.Cliente;
import org.bedu.postwork.model.Producto;
import org.bedu.postwork.services.ClienteService;
import org.bedu.postwork.services.ProductoService;
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

@AutoConfigureRestDocs
@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private  MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Test
    void getProducto() throws Exception {
        given(productoService.obtenProducto(anyLong())).willReturn(Optional.of(Producto.builder().id(1L).nombre("Nombre").categoria("categoria1").precio(1200.0F).build()));

        mockMvc.perform(get("/producto/{productoid}",1)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Nombre")))
                .andExpect(jsonPath("$.categoria", is("categoria1")))
                .andExpect(jsonPath("$.precio",is(1200.0)))

                .andDo(document("producto/get-producto",
                        pathParameters(
                                parameterWithName("productoid").description("Identificador del producto")
                        ),
                        responseFields(
                                fieldWithPath("id").description("identificador del producto"),
                                fieldWithPath("nombre").description("nombre del producto"),
                                fieldWithPath("categoria").description("categoria del producto"),
                                fieldWithPath("precio").description("precio del producto"),
                                fieldWithPath("numeroRegistro").description("numero del registro"),
                                fieldWithPath("fechaCreacion").description("fecha de creacion del producto")
                        )));
    }
    @Test
    void getProductos() throws Exception {

        List<Producto> productos = Arrays.asList(
                Producto.builder().id(1L).nombre("Nombre").categoria("categoria1").precio(1400.0F).build(),
                Producto.builder().id(2L).nombre("Nombre2").categoria("categoria1").precio(1300.0F).build(),
                Producto.builder().id(3L).nombre("Nombre3").categoria("categoria1").precio(1600.0F).build()
        );

        given(productoService.obtenProductos()).willReturn(productos);

        mockMvc.perform(get("/producto")
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[0].categoria", is("categoria1")))
                .andExpect(jsonPath("$[2].precio", is(1600.0)))

                .andDo(document("producto/get-productos",
                        responseFields(
                                fieldWithPath("[].id").description("identificador del producto"),
                                fieldWithPath("[].nombre").description("nombre del producto"),
                                fieldWithPath("[].categoria").description("categoria del producto"),
                                fieldWithPath("[].precio").description("precio del producto"),
                                fieldWithPath("[].numeroRegistro").description("numero del registro"),
                                fieldWithPath("[].fechaCreacion").description("fecha de creacion del producto")
                        )));
    }

    @Test
    void creaProducto() throws Exception {
        Producto productoParametro = Producto.builder().nombre("Nombre").categoria("categoria1").precio(1400.0F).build();
        Producto productoRespuesta = Producto.builder().id(1L).nombre("Nombre").categoria("categoria1").precio(1400.0F).build();

        given(productoService.guardaProducto(productoParametro)).willReturn(productoRespuesta);

        mockMvc.perform(post("/producto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productoParametro)))
                .andExpect(status().isCreated())
                .andDo(document("producto/post-producto",
                        requestFields(
                                fieldWithPath("id").description("identificador del producto"),
                                fieldWithPath("nombre").description("nombre del producto"),
                                fieldWithPath("categoria").description("categoria del producto"),
                                fieldWithPath("precio").description("precio del producto"),
                                fieldWithPath("numeroRegistro").description("numero del registro"),
                                fieldWithPath("fechaCreacion").description("fecha de creacion del producto")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("La ubicación del recurso (su identificador generado")
                        ))
                );
    }


    @Test
    void actualizaProducto() throws Exception {

        Producto productoParametro = Producto.builder().id(1L).nombre("Nombre").categoria("categoria1").precio(1400.0F).build();

        mockMvc.perform(put("/producto/{productoid}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productoParametro)))
                .andExpect(status().isNoContent())
                .andDo(document("producto/put-producto",
                        pathParameters(
                                parameterWithName("productoid").description("Identificador del producto")
                        ),
                        requestFields(
                                fieldWithPath("id").description("identificador del producto"),
                                fieldWithPath("nombre").description("nombre del producto"),
                                fieldWithPath("categoria").description("categoria del producto"),
                                fieldWithPath("precio").description("precio del producto"),
                                fieldWithPath("numeroRegistro").description("numero del registro"),
                                fieldWithPath("fechaCreacion").description("fecha de creacion del producto")
                        )
                ));
    }

    @Test
    void eliminaProducto() throws Exception {
        mockMvc.perform(delete("/producto/{productoid}", 1)
                        .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent())
                .andDo(document("producto/delete-producto",
                        pathParameters(
                                parameterWithName("productoid").description("Identificador del producto")
                        )));
    }



}
