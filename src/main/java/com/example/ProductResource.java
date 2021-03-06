package com.example;

import com.example.mock.DAOMock;
import com.example.mock.IDAOMock;
import com.example.model.Customers;
import com.example.mybatis.data.CustomersMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

@Path("product")
public class ProductResource {

    public ProductResource() {

        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = sqlSessionFactory.openSession();

            CustomersMapper mapper = session.getMapper(CustomersMapper.class);
            Customers result = mapper.selectCustomers(1);
            System.out.println(result);

            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProduct() {
        Product p = new Product();
        p.setId(200);
        p.setName("Java Do");
        p.setPrice(2800);
        return p;
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProducts() {
        IDAOMock dao = DAOMock.getInstance();
        return dao.select();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") int id) {
        IDAOMock dao = DAOMock.getInstance();
        try {
            Product p = dao.select(id);
            return Response.ok(p).build();
        } catch (Exception e) {
            e.printStackTrace();
            int status = 400;
            return Response.status(status).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postProducts(Product product) {
        IDAOMock dao = DAOMock.getInstance();
        try {
            dao.insert(product);
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            int status = 400;
            return Response.status(status).build();
        }
    }


    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putProduct(@PathParam("id") int id, Product product) {
        IDAOMock dao = DAOMock.getInstance();
        try {
            dao.update(id, product);
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            int status = 400;
            return Response.status(status).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteProduct(@PathParam("id") int id) {
        IDAOMock dao = DAOMock.getInstance();
        try {
            dao.delete(id);
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            int status = 400;
            return Response.status(status).build();
        }
    }
}
