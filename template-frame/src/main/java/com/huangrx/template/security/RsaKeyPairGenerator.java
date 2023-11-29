package com.huangrx.template.security;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;


/**
 * Rsa key生成
 *
 * @author huangrx
 * @since 2023-11-29 23:37
 */
@Slf4j
public class RsaKeyPairGenerator {

    public static void main(String[] args) {
        RSA rsa = SecureUtil.rsa();

        String privateKeyBase64 = rsa.getPrivateKeyBase64();
        String publicKeyBase64 = rsa.getPublicKeyBase64();

        // MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJFLb9y8ZAHqAWkOrSdBYcZdk0Nve+BXKyNmKXC67osudNPZWCHjr9/8ZzHxe33nGm4DJhJQdexzUivBhje/eFrKHEStNmUcSbkf1M5oWDLvjdLEeGYCJuB7mkaustVUw+c0ofblYl+cF5FHDiMjvP599Vq0QsFZzlPm5y8hULLPAgMBAAECgYAA6zMXCQq22bBIuZZQ2BQmAPGxguJfO9sXkDvWi6viyaN7LXXPy6g9rgWKgkkTsDwNWrAiWvKiNT3HMKmsllpuDeyYjLVm6rA8+dixtDwdrEER7/LfKwBIWLU7XyyZslQduoXc7t7JjtTh1qtlqUqOiG+1BNclyLTR72Z04kjwPQJBALeDmmOFfkUYEHl/2BW2R5vNvxw2J1/mJK8cA6wC/1kGLUX8wI2CYN9hw+O/kVkUWlk5Ft61zzcm/7+Pe7L4mEsCQQDKrzO4xD4rEh6c5L5ruNG7GUab+0siYlVd9o0aeUJOeRTXn/mpF7d5yhUpY5wtSDUkcHwqv+03bm+dUURzdoUNAkEAlSXQhLIqPd2hBh5xcLQE3wwJFtSsi7RwfCE4msSU47WxdgBGPryIHwVGQAOiDdQM2hccD0WUdC6uDPKAhqSkoQJBAI3tycYkhXQbqBwtgbplHVhrBqrLeX2GZkj6ESzspKXW2+VyPJ1P4kcCX3cwLXj4C16M9iXpZ+9dAgOjmM9RuzECQA1lT4vliW0HsjFEgGRLguhob3mKU7R8q5YJgoxbyu9Utn3361tYqMrYF9jqreKBkcjxr3PsYw4J4wcdFf1MPqI=
        log.info(privateKeyBase64);
        // MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCRS2/cvGQB6gFpDq0nQWHGXZNDb3vgVysjZilwuu6LLnTT2Vgh46/f/Gcx8Xt95xpuAyYSUHXsc1IrwYY3v3hayhxErTZlHEm5H9TOaFgy743SxHhmAibge5pGrrLVVMPnNKH25WJfnBeRRw4jI7z+ffVatELBWc5T5ucvIVCyzwIDAQAB
        log.info(publicKeyBase64);
    }


}