package ru.ifmo.insys1.api;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.ifmo.insys1.entity.MovieGenre;
import ru.ifmo.insys1.request.MovieRequest;
import ru.ifmo.insys1.security.JWT;

import static ru.ifmo.insys1.constants.APIConstants.MOVIES_URI;

@Path(MOVIES_URI)
public interface MovieController {

    @GET
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response getMovie(@PathParam("id") Long id);

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response getAllMovies(@QueryParam("page") @DefaultValue("1") int page,
                          @QueryParam("size") @DefaultValue("10") int size);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response createMovie(@Valid MovieRequest movie);

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @JWT
    Response updateMovie(@PathParam("id") Long id, MovieRequest movie);

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response deleteMovie(@PathParam("id") Long id);

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @JWT
    Response deleteMovieByTagline(@QueryParam("tagline") String tagline);

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response getCountMoviesWithTagline(@QueryParam("tagline") String tagline);

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response getCountMoviesWithGenre(@QueryParam("genre") MovieGenre movieGenre);

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response getOperatorsWithoutOscar();

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response incrementOscarsCountForAllMoviesWithRCategory();
}
