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
@Produces(MediaType.APPLICATION_JSON)
public interface MovieController {

    @GET
    @Path("/{id}")
    @JWT
    Response getMovie(@PathParam("id") Long id);

    @GET
    @JWT
    Response getAllMovies(@QueryParam("page") @DefaultValue("1") int page,
                          @QueryParam("size") @DefaultValue("10") int size,
                          @QueryParam("filter-value") String filter,
                          @QueryParam("filter-column") String filterColumn,
                          @QueryParam("sorted") String sorted
    );

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @JWT
    Response createMovie(@Valid MovieRequest movie);

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @JWT
    Response updateMovie(@QueryParam("id") Long id, MovieRequest movie);

    @DELETE
    @JWT
    Response deleteMovie(@QueryParam("id") Long id);

    @DELETE
    @Path("/by-tagline")
    @JWT
    Response deleteMovieByTagline(@QueryParam("tagline") String tagline);

    @GET
    @Path("/count-by-tagline")
    @JWT
    Response getCountMoviesWithTagline(@QueryParam("tagline") String tagline);

    @GET
    @Path("/count-by-genre")
    @JWT
    Response getCountMoviesWithGenre(@QueryParam("genre") MovieGenre movieGenre);

    @PATCH
    @Path("/add-oscar-to-R")
    @JWT
    Response incrementOscarsCountForAllMoviesWithRCategory();
}
