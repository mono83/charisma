package com.github.mono83.charisma.readers;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.mono83.charisma.Values;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * YAML implementation of template reader.
 *
 * @param <T> Values type.
 */
public class TemplateReaderYAML<T extends Enum<T>> implements TemplateReader<T> {
    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private final JavaType type;

    public TemplateReaderYAML(final Class<T> clazz) {
        type = mapper.getTypeFactory().constructCollectionType(
                List.class,
                mapper.getTypeFactory().constructMapType(
                        EnumMap.class,
                        clazz,
                        Long.class
                )
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<? extends Values<T>> read(final InputStream source) throws IOException {
        return ((List<EnumMap<T, Long>>) mapper.readValue(source, type))
                .stream()
                .map(Values::from)
                .collect(Collectors.toList());
    }
}
