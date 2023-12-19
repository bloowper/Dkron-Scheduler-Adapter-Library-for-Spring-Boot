package com.github.bloowper.schedulerclient;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

//TODO provide normal data types
@RequiredArgsConstructor
@Data
@Builder
class HttpExecutorConfigDto implements ExecutorConfig {
    private final String method;
    private final String url;
    private final String headers;
    private final String body;
    private final String timeout;
    private final String expectCode;
    private final String expectBody;
    private final String debug;
    private final String tlsNoVerifyPeer;
    private final String tlsCertificateFile;
    private final String tlsCertificateKeyFile;
    private final String tlsRootCAsFile;
}
