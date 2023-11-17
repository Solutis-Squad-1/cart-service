package br.com.solutis.squad1.cartservice.dto.product;

/**
 * DTO for creating a image.
 */
public record ImageResponseDto(
        Long id,
        String archiveName,
        String originalName,
        String contentType,
        Long size,
        String url
) {
}
