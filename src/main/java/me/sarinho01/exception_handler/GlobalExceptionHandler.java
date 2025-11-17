package me.sarinho01.exception_handler;

import jakarta.validation.constraints.NotNull;
import me.sarinho01.utils.resutil.DefaultResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({BasicHttpException.class})
	public ResponseEntity<DefaultResponse> basicHttpExceptionHandler(BasicHttpException exception) {
		return ResponseEntity.status(exception.getHttpStatus())
				.body(new DefaultResponse(exception.getTitle(), exception.getMessage(), null));
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<DefaultResponse> exceptionHandler(Exception exception) {
		exception.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new DefaultResponse(exception.toString(), exception.getMessage(), null));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException ex,
			@NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
		Map<String, String> errors = getErrorsMap(ex);
		DefaultResponse response = new DefaultResponse("Invalid arguments", "Invalid arguments in request body",
				errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	private Map<String, String> getErrorsMap(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return errors;
	}
}
