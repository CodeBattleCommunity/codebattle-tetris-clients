package com.codenjoy.dojo.tetris.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UrlParser {
    public static final String WSS_PROTOCOL = "wss";
    public static final String WS_PROTOCOL = "ws";
    public static final String HTTPS_PROTOCOL = "https";

    public String protocol;
    public String server;
    public String code;
    public String userName;
    public String context;

    public UrlParser(String uri) {
        try {
            URL url = new URL(uri);
            String query = url.getQuery();
            if (query == null) {
                throw badUrl();
            }
            Map<String, String> queryParts = Stream.of(query.split("&"))
                    .map(p -> p.split("="))
                    .collect(Collectors.toMap(p -> p[0], p -> p[1]));
            String[] urlParts = url.getPath().split("\\/");
            if (urlParts.length != 5
                    || !urlParts[0].equals("")
                    || !urlParts[2].equals("board")
                    || !urlParts[3].equals("player")
                    || queryParts.get("code") == null) {
                throw badUrl();
            }

            protocol = (url.getProtocol().equals(HTTPS_PROTOCOL)) ? WSS_PROTOCOL : WS_PROTOCOL;
            server = url.getHost() + portPart(url.getPort());
            code = queryParts.get("code");
            userName = urlParts[4];
            context = urlParts[1];
        } catch (MalformedURLException e) {
            throw new RuntimeException("Please set url in format " +
                    "'http://codenjoyDomainOrIP:8080/codenjoy-contest/" +
                    "board/player/3edq63tw0bq4w4iem7nb?code=12345678901234567890'",
                    e);
        }
    }

    private IllegalArgumentException badUrl() {
        return new IllegalArgumentException("Bad web socket server url, expected: http://server:port/codenjoy-contest/board/player/playerid?code=12345678901234567890&gameName=game");
    }

    private String portPart(int port) {
        return (port == -1) ? "" : (":" + port);
    }

    @Override
    public String toString() {
        return "UrlParser{" +
                "protocol='" + protocol + '\'' +
                ", server='" + server + '\'' +
                ", code='" + code + '\'' +
                ", userName='" + userName + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}
