﻿package com.jianglibo.wx.json;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class TestProjectionJson {

//    public static class FooWrapper {
//        private Foo f;
//
//        public FooWrapper(Foo f) {
//            this.f = f;
//        }
//
//        public Foo getF() {
//            return f;
//        }
//
//        public void setF(Foo f) {
//            this.f = f;
//        }
//
//    }
//
//    @Test
//    public void t() throws JsonProcessingException {
//        ObjectMapper om = new ObjectMapper();
//        
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(new FooSerializer());
//        
////        om.registerModule(module);
//        
//        om.registerModule(new Pmodule());
//
//        FooWrapper fw = new FooWrapper(new Foo("abc"));
//        
//        Foo foo = new Foo("abc"); 
//
//        String jn = om.writeValueAsString(foo);
//
//        System.out.println(jn);
//    }
//
//    /**
//     * 
//     * @author jianglibo@gmail.com
//     *
//     */
//    public static class Pmodule extends SimpleModule {
//
//        /**
//         * 
//         */
//        private static final long serialVersionUID = 1L;
//
//        /**
//         * 
//         */
//        public Pmodule() {
//            super(new Version(1, 0, 0, null, "hello", "jackson-module-mine"));
//        }
//
//        /*
//         * (non-Javadoc)
//         * 
//         * @see com.fasterxml.jackson.databind.module.SimpleModule#setupModule(com.fasterxml.jackson.databind.Module.SetupContext)
//         */
//        @Override
//        public void setupModule(SetupContext context) {
//            addSerializer(new FooSerializer());
//            super.setupModule(context);
//        }
//
//    }
//
//    public static class FooSerializer extends StdSerializer<Foo> {
//
//        /**
//         * @param t
//         */
//        public FooSerializer() {
//            super(Foo.class);
//        }
//
//        /*
//         * (non-Javadoc)
//         * 
//         * @see com.fasterxml.jackson.databind.ser.std.StdSerializer#serialize(java.lang.Object, com.fasterxml.jackson.core.JsonGenerator,
//         * com.fasterxml.jackson.databind.SerializerProvider)
//         */
//        @Override
//        public void serialize(Foo value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
//            JsonSerializer js = provider.findValueSerializer(FooSimple.class, null);//.unwrappingSerializer(null);
//            js.serialize(value, jgen, provider);
//        }
//    }
}
