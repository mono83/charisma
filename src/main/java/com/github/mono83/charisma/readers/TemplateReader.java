package com.github.mono83.charisma.readers;

import com.github.mono83.charisma.Values;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Defines components, that can be used to read templates.
 */
public interface TemplateReader<T extends Enum<T>> {
    /**
     * Reads collection of values from given input stream source.
     *
     * @param source Source input stream.
     * @return Collection of values.
     * @throws IOException On IO error.
     */
    Collection<? extends Values<T>> read(InputStream source) throws IOException;

    /**
     * Reads collection of objects from given input stream source.
     *
     * @param source  Source input stream.
     * @param builder Builder used to convert values into objects.
     * @return Collection of objects.
     * @throws IOException On IO error.
     */
    default <Z> Collection<Z> read(final InputStream source, final Function<Values<T>, Z> builder) throws IOException {
        return this.read(source).stream().map(builder).collect(Collectors.toList());
    }

    /**
     * Reads collection of values from given string.
     *
     * @param s Source data.
     * @return Collection of values.
     * @throws IOException On IO error.
     */
    default Collection<? extends Values<T>> readString(final String s) throws IOException {
        try (InputStream is = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8))) {
            return read(is);
        }
    }

    /**
     * Reads collection of objects from given string.
     *
     * @param s       Source data.
     * @param builder Builder used to convert values into objects.
     * @return Collection of objects.
     * @throws IOException On IO error.
     */
    default <Z> Collection<Z> readString(final String s, final Function<Values<T>, Z> builder) throws IOException {
        try (InputStream is = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8))) {
            return this.read(is).stream().map(builder).collect(Collectors.toList());
        }
    }

    /**
     * Reads collection of values from resource.
     *
     * @param name Resource name.
     * @return Collection of values.
     * @throws IOException On IO error.
     */
    default Collection<? extends Values<T>> readResource(final String name) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(name)) {
            return read(is);
        }
    }

    /**
     * Reads collection of objects from given input stream source.
     *
     * @param name    Resource name.
     * @param builder Builder used to convert values into objects.
     * @return Collection of objects.
     * @throws IOException On IO error.
     */
    default <Z> Collection<Z> readResource(final String name, final Function<Values<T>, Z> builder) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(name)) {
            return this.read(is).stream().map(builder).collect(Collectors.toList());
        }
    }

    /**
     * Reads collection of values from file.
     *
     * @param name File name.
     * @return Collection of values.
     * @throws IOException On IO error.
     */
    default Collection<? extends Values<T>> readFile(final String name) throws IOException {
        try (FileInputStream is = new FileInputStream(name)) {
            return read(is);
        }
    }

    /**
     * Reads collection of objects from given input stream source.
     *
     * @param name    File name.
     * @param builder Builder used to convert values into objects.
     * @return Collection of objects.
     * @throws IOException On IO error.
     */
    default <Z> Collection<Z> readFile(final String name, final Function<Values<T>, Z> builder) throws IOException {
        try (FileInputStream is = new FileInputStream(name)) {
            return this.read(is).stream().map(builder).collect(Collectors.toList());
        }
    }
}
