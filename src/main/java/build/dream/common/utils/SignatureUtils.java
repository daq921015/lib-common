package build.dream.common.utils;

import build.dream.common.constants.Constants;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class SignatureUtils {
    public static final String SIGNATURE_ALGORITHM_SHA256_WITH_RSA = "SHA256withRSA";
    public static final String SIGNATURE_ALGORITHM_SHA1_WITH_RSA = "SHA1WithRSA";
    public static final String SIGNATURE_TYPE_SHA256_WITH_RSA = "SHA256withRSA";
    public static final String SIGNATURE_TYPE_SHA1_WITH_RSA = "SHA1WithRSA";
    public static final String OUTPUT_TYPE_BASE64 = "base64";
    public static final String OUTPUT_TYPE_HEX = "hex";

    public static Signature obtainSignature(String signatureType) throws NoSuchAlgorithmException {
        if (SIGNATURE_TYPE_SHA256_WITH_RSA.equals(signatureType)) {
            return Signature.getInstance(SIGNATURE_ALGORITHM_SHA256_WITH_RSA);
        } else if (SIGNATURE_TYPE_SHA1_WITH_RSA.equals(signatureType)) {
            return Signature.getInstance(SIGNATURE_ALGORITHM_SHA1_WITH_RSA);
        } else {
            throw new IllegalArgumentException("不支持的签名方式：" + signatureType);
        }
    }

    public static String sign(String data, String privateKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException {
        return sign(data, privateKey, SIGNATURE_ALGORITHM_SHA1_WITH_RSA, OUTPUT_TYPE_BASE64);
    }

    public static String sign(String data, String privateKey, String signatureType, String outputType) throws InvalidKeySpecException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, SignatureException {
        return sign(data, RSAUtils.restorePrivateKey(privateKey), signatureType, outputType);
    }

    public static String sign(String data, PrivateKey privateKey) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        return sign(data, privateKey, SIGNATURE_ALGORITHM_SHA1_WITH_RSA, OUTPUT_TYPE_BASE64);
    }

    public static String sign(String data, PrivateKey privateKey, String signatureType, String outputType) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, SignatureException {
        Signature signature = obtainSignature(signatureType);
        signature.initSign(privateKey);
        signature.update(data.getBytes(Constants.CHARSET_UTF_8));
        if (OUTPUT_TYPE_BASE64.equals(outputType)) {
            return Base64.encodeBase64String(signature.sign());
        } else if (OUTPUT_TYPE_HEX.equals(outputType)) {
            return Hex.encodeHexString(signature.sign());
        } else {
            throw new IllegalArgumentException("不支持的输出方式：" + outputType);
        }
    }

    public static boolean verifySign(String data, String publicKey, String signed) throws NoSuchAlgorithmException, SignatureException, InvalidKeySpecException, InvalidKeyException, DecoderException, UnsupportedEncodingException {
        return verifySign(data, publicKey, SIGNATURE_TYPE_SHA1_WITH_RSA, OUTPUT_TYPE_BASE64, signed);
    }

    public static boolean verifySign(String data, String publicKey, String signatureType, String outputType, String signed) throws InvalidKeySpecException, NoSuchAlgorithmException, DecoderException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        return verifySign(data, RSAUtils.restorePublicKey(publicKey), signatureType, outputType, signed);
    }

    public static boolean verifySign(String data, PublicKey publicKey, String signed) throws DecoderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        return verifySign(data, publicKey, SIGNATURE_TYPE_SHA1_WITH_RSA, OUTPUT_TYPE_BASE64, signed);
    }

    public static boolean verifySign(String data, PublicKey publicKey, String signatureType, String outputType, String signed) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, DecoderException, UnsupportedEncodingException {
        Signature signature = obtainSignature(signatureType);
        signature.initVerify(publicKey);
        signature.update(data.getBytes(Constants.CHARSET_UTF_8));
        if (OUTPUT_TYPE_BASE64.equals(outputType)) {
            return signature.verify(Base64.decodeBase64(signed));
        } else if (OUTPUT_TYPE_HEX.equals(outputType)) {
            return signature.verify(Hex.decodeHex(signed.toCharArray()));
        } else {
            throw new IllegalArgumentException("不支持的输出方式：" + outputType);
        }
    }
}