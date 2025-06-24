package com.book.convert;

import com.book.dto.BookDTO;
import com.book.po.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring") // 使用Spring组件模型
public interface BookMapper {

    // 默认实例（如果不使用Spring依赖注入）
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "publicationDate", dateFormat = "yyyy-MM-dd")
    BookDTO toDTO(Book book);

    @Mapping(target = "publicationDate", dateFormat = "yyyy-MM-dd")
    Book toEntity(BookDTO bookDTO);

    List<BookDTO> toDTOList(List<Book> books);
}