package com.irdeto.jumpstart.workflow.ingest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class IngestFactory<IM extends IngestMapper> {
	private static final Logger logger = LoggerFactory.getLogger(IngestFactory.class);

	abstract protected List<Class<IM>> getIngestMapperClassList();

	public IM getMapper(String xmlString, String fileName, String filePath) {
		if (xmlString == null) {
			return null;
		}
		for (Class<IM> ingestMapperClass : getIngestMapperClassList()) {
			try {
				Constructor<IM> ingestMapperConstructor = ingestMapperClass.getConstructor();
				IngestMapper ingestMapper = ingestMapperConstructor.newInstance();
				ingestMapper.setInputString(stripUTF8ByteOrderMark(xmlString));
				ingestMapper.setFileName(fileName);
				ingestMapper.setFilePath(filePath);
				if (ingestMapper.isApplicableMapper()) {
					logger.debug("Using ingest mapper {}.", ingestMapper.getClass().getSimpleName());
					return (IM) ingestMapper;
				}
			} catch (NoSuchMethodException | SecurityException
					| InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				logger.error("Unable to use ingest mapper {}", ingestMapperClass.getCanonicalName());
			}
		}
		return null;
	}

	public static String stripUTF8ByteOrderMark(String input){
		String output = input;
		byte[] inbytes = input.getBytes();
		// UTF8 marker
		if ((inbytes[0] == 0xEF) &&
				(inbytes[1] == 0xBB) &&
				(inbytes[2] == 0xBF)){
			byte[] outbytes = new byte[inbytes.length - 3];
			System.arraycopy(inbytes,3, outbytes,0, inbytes.length - 3);
			output = new String(outbytes);
			return output;
		}
		return output;
	}
}
