package by.itechart.api.util.annotation;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", defaultValue = "0", value = "Results page you want to retrieve (0..N)"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", defaultValue = "10", value = "Number of records per page."),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
                + "Default sort order is ascending.")})
public @interface ApiPageable {

}
