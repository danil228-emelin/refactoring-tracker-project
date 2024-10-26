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
    @JWT
    Response updateMovie(@QueryParam("id") Long id, MovieRequest movie);

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response deleteMovie(@QueryParam("id") Long id);

    @DELETE
    @Path("/by-tagline")
    @Consumes(MediaType.APPLICATION_JSON)
    @JWT
    Response deleteMovieByTagline(@QueryParam("tagline") String tagline);

    @GET
    @Path("/count-by-tagline")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response getCountMoviesWithTagline(@QueryParam("tagline") String tagline);

    @GET
    @Path("/count-by-genre")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
//    @JWT
    Response getCountMoviesWithGenre(@QueryParam("genre") MovieGenre movieGenre);

    @PATCH
    @Path("/add-oscar-to-R")
    @Produces(MediaType.APPLICATION_JSON)
    @JWT
    Response incrementOscarsCountForAllMoviesWithRCategory();
}
