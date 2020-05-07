package com.study.app.json.moshi;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import com.study.app.json.moshi.adapter.MoshiLenientJsonAdapterFactory;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@SuppressWarnings("ConstantConditions")
public class JsonAdapterTest {
    /**
     * 普通的JsonAdapter
     */
    private JsonAdapter<Data> jsonAdapter = new Moshi.Builder().build().adapter(Data.class);
    /**
     * 支持json格式容忍的JsonAdapter
     */
    private JsonAdapter<Data> lenientPhpJsonAdapter = MoshiLenientJsonAdapterFactory
            .addLenientJsonAdapters(new Moshi.Builder().build())
            .adapter(Data.class);


    @Test
    public void fromJson_intFromDecimal() throws Exception {
        System.out.println("fromJson_intFromDecimal  ***START***");

        // int <- 1.0，Moshi 本身支持
        {
            Data data = jsonAdapter.fromJson("{\"iNum\": 1.0}");
            assertEquals(1, data.iNum);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.iNum = 1;
            assertEquals(expectedData, data);

            System.out.println("int <- 1.0，Moshi 本身支持  ***END***");
        }

        // long <- 1.0，Moshi 本身支持
        {
            Data data = jsonAdapter.fromJson("{\"lNum\": 1.0}");
            assertEquals(1L, data.lNum);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.lNum = 1L;
            assertEquals(expectedData, data);

            System.out.println("long <- 1.0，Moshi 本身支持  ***END***");
        }

        // Integer <- 1.0，Moshi 本身支持
        {
            Data data = jsonAdapter.fromJson("{\"iNum2\": 1.0}");
            assertEquals((Integer) 1, data.iNum2);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.iNum2 = 1;
            assertEquals(expectedData, data);

            System.out.println("Integer <- 1.0，Moshi 本身支持  ***END***");
        }

        // Long <- 1.0，Moshi 本身支持
        {
            Data data = jsonAdapter.fromJson("{\"lNum2\": 1.0}");
            assertEquals((Long) 1L, data.lNum2);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.lNum2 = 1L;
            assertEquals(expectedData, data);

            System.out.println("Long <- 1.0，Moshi 本身支持  ***END***");
        }

        System.out.println("fromJson_intFromDecimal  ***END***");
    }

    @Test
    public void fromJsonLenientPhp_intFromDecimal() throws Exception {
        System.out.println("fromJsonLenientPhp_intFromDecimal  ***START***");

        // int <- 1.0，Moshi 本身支持
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"iNum\": 1.0}");
            assertEquals(1, data.iNum);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.iNum = 1;
            assertEquals(expectedData, data);

            System.out.println("int <- 1.0，Moshi 本身支持  ***END***");
        }

        // long <- 1.0，Moshi 本身支持
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"lNum\": 1.0}");
            assertEquals(1L, data.lNum);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.lNum = 1L;
            assertEquals(expectedData, data);

            System.out.println("long <- 1.0，Moshi 本身支持  ***END***");
        }

        // Integer <- 1.0，Moshi 本身支持
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"iNum2\": 1.0}");
            assertEquals((Integer) 1, data.iNum2);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.iNum2 = 1;
            assertEquals(expectedData, data);

            System.out.println("Integer <- 1.0，Moshi 本身支持  ***END***");
        }

        // Long <- 1.0，Moshi 本身支持
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"lNum2\": 1.0}");
            assertEquals((Long) 1L, data.lNum2);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.lNum2 = 1L;
            assertEquals(expectedData, data);

            System.out.println("Long <- 1.0，Moshi 本身支持  ***END***");
        }

        System.out.println("fromJsonLenientPhp_intFromDecimal  ***END***");
    }

    @Test
    public void fromJson_intFromString() throws Exception {
        System.out.println("fromJson_intFromString  ***START***");

        // int <- "1.0"，Moshi 本身支持
        {
            Data data = jsonAdapter.fromJson("{\"iNum\": \"1.0\"}");
            assertEquals(1, data.iNum);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.iNum = 1;
            assertEquals(expectedData, data);

            System.out.println("int <- \"1.0\"，Moshi 本身支持  ***END***");
        }

        // long <- "1.0"，Moshi 本身支持
        {
            Data data = jsonAdapter.fromJson("{\"lNum\": \"1.0\"}");
            assertEquals(1L, data.lNum);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.lNum = 1L;
            assertEquals(expectedData, data);

            System.out.println("long <- \"1.0\"，Moshi 本身支持  ***END***");
        }

        // Integer <- "1.0"，Moshi 本身支持
        {
            Data data = jsonAdapter.fromJson("{\"iNum2\": \"1.0\"}");
            assertEquals((Integer) 1, data.iNum2);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.iNum2 = 1;
            assertEquals(expectedData, data);

            System.out.println("Integer <- \"1.0\"，Moshi 本身支持  ***END***");
        }

        // Long <- "1.0"，Moshi 本身支持
        {
            Data data = jsonAdapter.fromJson("{\"lNum2\": \"1.0\"}");
            assertEquals((Long) 1L, data.lNum2);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.lNum2 = 1L;
            assertEquals(expectedData, data);

            System.out.println("Long <- \"1.0\"，Moshi 本身支持  ***END***");
        }

        System.out.println("fromJson_intFromString  ***END***");
    }

    @Test
    public void fromJsonLenientPhp_intFromString() throws Exception {
        System.out.println("fromJsonLenientPhp_intFromString  ***START***");

        // int <- "1.0"，Moshi 本身支持
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"iNum\": \"1.0\"}");
            assertEquals(1, data.iNum);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.iNum = 1;
            assertEquals(expectedData, data);

            System.out.println("int <- \"1.0\"，Moshi 本身支持  ***END***");
        }

        // long <- "1.0"，Moshi 本身支持
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"lNum\": \"1.0\"}");
            assertEquals(1L, data.lNum);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.lNum = 1L;
            assertEquals(expectedData, data);

            System.out.println("long <- \"1.0\"，Moshi 本身支持  ***END***");
        }

        // Integer <- "1.0"，Moshi 本身支持
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"iNum2\": \"1.0\"}");
            assertEquals((Integer) 1, data.iNum2);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.iNum2 = 1;
            assertEquals(expectedData, data);

            System.out.println("Integer <- \"1.0\"，Moshi 本身支持  ***END***");
        }

        // Long <- "1.0"，Moshi 本身支持
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"lNum2\": \"1.0\"}");
            assertEquals((Long) 1L, data.lNum2);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.lNum2 = 1L;
            assertEquals(expectedData, data);

            System.out.println("Long <- \"1.0\"，Moshi 本身支持  ***END***");
        }

        System.out.println("fromJsonLenientPhp_intFromString  ***END***");
    }

    @Test
    public void fromJson_intFromEmptyString() throws Exception {
        System.out.println("fromJson_intFromEmptyString  ***START***");

        // int <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"iNum\": \"\"}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected an int but was  at path $.iNum", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("int <- \"\" Moshi 本身不支持 ***END***");

        // long <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"lNum\": \"\"}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected a long but was  at path $.lNum", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("long <- \"\" Moshi 本身不支持 ***END***");

        // Integer <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"iNum2\": \"\"}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected an int but was  at path $.iNum2", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("Integer <- \"\" Moshi 本身不支持 ***END***");

        // Long <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"lNum2\": \"\"}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected a long but was  at path $.lNum2", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("Long <- \"\" Moshi 本身不支持 ***END***");

        System.out.println("fromJson_intFromEmptyString  ***END***");
    }

    @Test
    public void fromJsonLenientPhp_intFromEmptyString() throws Exception {
        System.out.println("fromJsonLenientPhp_intFromEmptyString  ***START***");

        // int <- "", 此时："" 视为 null，仍然拒绝
        // 此处与 GsonUtil 不一样，Gson 会忽略此时的 null
        try {
            Data data = lenientPhpJsonAdapter.fromJson("{\"iNum\": \"\"}");
            assertEquals(0, data.iNum);
            System.out.println("int <- \"\" data.iNum:" + data.iNum);
        } catch (JsonDataException e) {
            assertEquals("Expected an int but was  at path $.iNum", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("int <- \"\" ***END***");

        // long <- "", 此时："" 视为 null，仍然拒绝
        // 此处与 GsonUtil 不一样，Gson 会忽略此时的 null
        try {
            Data data = lenientPhpJsonAdapter.fromJson("{\"lNum\": \"\"}");
            System.out.println("long <- \"\" data.lNum:" + data.lNum);
        } catch (JsonDataException e) {
            assertEquals("Expected a long but was  at path $.lNum", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("long <- \"\" ***END***");

        // Integer <- "", 此时："" 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"iNum2\": \"\"}");
            System.out.println("Integer <- \"\" data.iNum2:" + (data.iNum2 == null ? "null" : data.iNum2));
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.iNum2 = null;
            assertEquals(expectedData, data);
        }
        System.out.println("Integer <- \"\" ***END***");

        // Long <- "", 此时："" 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"lNum2\": \"\"}");
            System.out.println("Long <- \"\" data.lNum2:" + (data.lNum2 == null ? "null" : data.lNum2));
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.lNum2 = null;
            assertEquals(expectedData, data);
        }
        System.out.println("Long <- \"\" ***END***");
        System.out.println("fromJsonLenientPhp_intFromEmptyString  ***END***");
    }

    @Test
    public void fromJson_floatFromEmptyString() throws Exception {
        System.out.println("fromJson_floatFromEmptyString  ***START***");

        /**
         * 以下Moshi本身不支持
         */

        // float <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"fNum\": \"\"}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            // Double.parseDouble
            // JsonUtf8Reader.nextDouble(JsonUtf8Reader.java:763)
            assertEquals("Expected a double but was  at path $.fNum", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("float <- \"\" Moshi本身不支持 ***END***");

        // double <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"dNum\": \"\"}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            // Double.parseDouble
            // JsonUtf8Reader.nextDouble(JsonUtf8Reader.java:763)
            assertEquals("Expected a double but was  at path $.dNum", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("double <- \"\" Moshi本身不支持 ***END***");

        // Float <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"fNum2\": \"\"}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            // Double.parseDouble
            // JsonUtf8Reader.nextDouble(JsonUtf8Reader.java:763)
            assertEquals("Expected a double but was  at path $.fNum2", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("Float <- \"\" Moshi本身不支持 ***END***");

        // Double <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"dNum2\": \"\"}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            // Double.parseDouble
            // JsonUtf8Reader.nextDouble(JsonUtf8Reader.java:763)
            assertEquals("Expected a double but was  at path $.dNum2", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("Double <- \"\" Moshi本身不支持 ***END***");

        System.out.println("fromJson_floatFromEmptyString  ***END***");
    }

    @Test
    public void fromJsonLenientPhp_floatFromEmptyString() throws Exception {
        System.out.println("fromJsonLenientPhp_floatFromEmptyString  ***END***");

        // float <- "", 此时："" 视为 null，仍然拒绝
        // 此处与 GsonUtil 不一样，Gson 会忽略此时的 null
        try {
            Data data = lenientPhpJsonAdapter.fromJson("{\"fNum\": \"\"}");
            System.out.println("float <- \"\" data.fNum:" + data.fNum);
        } catch (JsonDataException e) {
            // Double.parseDouble
            // JsonUtf8Reader.nextDouble(JsonUtf8Reader.java:763)
            assertEquals("Expected a double but was  at path $.fNum", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("float <- \"\" ***END***");

        // double <- "", 此时："" 视为 null，仍然拒绝
        // 此处与 GsonUtil 不一样，Gson 会忽略此时的 null
        try {
            Data data = lenientPhpJsonAdapter.fromJson("{\"dNum\": \"\"}");
            System.out.println("double <- \"\" data.dNum:" + data.dNum);
        } catch (JsonDataException e) {
            // Double.parseDouble
            // JsonUtf8Reader.nextDouble(JsonUtf8Reader.java:763)
            assertEquals("Expected a double but was  at path $.dNum", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("double <- \"\" ***END***");

        // Float <- "", 此时："" 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"fNum2\": \"\"}");
            System.out.println("Float <- \"\" data.fNum2:" + (data.fNum2 == null ? "null" : data.fNum2));
            assertNull(data.fNum2);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.fNum2 = null;
            assertEquals(expectedData, data);
        }
        System.out.println("Float <- \"\" ***END***");

        // Double <- "", 此时："" 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"dNum2\": \"\"}");
            System.out.println("Double <- \"\" data.dNum2:" + (data.dNum2 == null ? "null" : data.dNum2));
            assertNull(data.dNum2);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.dNum2 = null;
            assertEquals(expectedData, data);
        }
        System.out.println("Double <- \"\" ***END***");

        System.out.println("fromJsonLenientPhp_floatFromEmptyString  ***END***");
    }

    @Test
    public void fromJson_arrayFromEmptyString() throws Exception {
        System.out.println("fromJson_arrayFromEmptyString  ***START***");

        // [] <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"strArr\": \"\"}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected BEGIN_ARRAY but was STRING at path $.strArr", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("[] <- \"\" Moshi本身不支持 ***END***");
        System.out.println("fromJson_arrayFromEmptyString  ***END***");
    }

    @Test
    public void fromJsonLenientPhp_arrayFromEmptyString() throws Exception {
        System.out.println("fromJsonLenientPhp_arrayFromEmptyString  ***START***");

        // [] <- "", 此时："" 视为 []
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"strArr\": \"\"}");
            System.out.println("[] <- \"\" data.strArr:" + (data.strArr == null ? "null" : Arrays.toString(data.strArr)));
            assertArrayEquals(new String[0], data.strArr);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.strArr = new String[0];
            assertEquals(expectedData, data);
        }
        System.out.println("[] <- \"\" ***END***");

        System.out.println("fromJsonLenientPhp_arrayFromEmptyString  ***END***");
    }

    @Test
    public void fromJson_listFromEmptyString() throws Exception {
        System.out.println("fromJson_listFromEmptyString  ***START***");

        // [] <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"strList\": \"\"}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected BEGIN_ARRAY but was STRING at path $.strList", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("[] <- \"\" Moshi本身不支持 ***END***");
        System.out.println("fromJson_listFromEmptyString  ***END***");
    }

    @Test
    public void fromJsonLenientPhp_listFromEmptyString() throws Exception {
        System.out.println("fromJsonLenientPhp_listFromEmptyString  ***START***");

        // [] <- "", 此时："" 视为 []
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"strList\": \"\"}");
            System.out.println("[] <- \"\" data.strList:" + (data.strList == null ? "null" : data.strList));
            assertEquals(Collections.emptyList(), data.strList);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.strList = Collections.emptyList();
            assertEquals(expectedData, data);
        }

        System.out.println("[] <- \"\" ***END***");

        System.out.println("fromJsonLenientPhp_listFromEmptyString  ***END***");
    }

    @Test
    public void fromJson_mapFromEmptyString() throws Exception {
        System.out.println("fromJson_mapFromEmptyString  ***START***");

        // {} <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"strMap\": \"\"}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected BEGIN_OBJECT but was STRING at path $.strMap", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("{} <- \"\" Moshi本身不支持 ***END***");

        System.out.println("fromJson_mapFromEmptyString  ***END***");
    }

    @Test
    public void fromJsonLenientPhp_mapFromEmptyString() throws Exception {
        System.out.println("fromJsonLenientPhp_mapFromEmptyString  ***START***");

        // {} <- "", 此时："" 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"strMap\": \"\"}");
            System.out.println("{} <- \"\" data.strMap:" + (data.strMap == null ? "null" : data.strMap));
            assertNull(data.strMap);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.strMap = null;
            assertEquals(expectedData, data);
        }

        System.out.println("{} <- \"\" ***END***");

        System.out.println("fromJsonLenientPhp_mapFromEmptyString  ***END***");
    }

    @Test
    public void fromJson_mapFromArray() throws Exception {
        System.out.println("fromJson_mapFromArray  ***START***");

        // {} <- []，Moshi 拒绝
        try {
            Data data = jsonAdapter.fromJson("{\"strMap\": []}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected BEGIN_OBJECT but was BEGIN_ARRAY at path $.strMap", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("{} <- []，Moshi 拒绝 ***END***");

        // {} <- [["a","b"]]，Moshi 拒绝
        try {
            Data data = jsonAdapter.fromJson("{\"strMap\": [[\"a\",\"b\"]]}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected BEGIN_OBJECT but was BEGIN_ARRAY at path $.strMap", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }

        System.out.println("{} <- [[\"a\",\"b\"]]，Moshi 拒绝 ***END***");

        System.out.println("fromJson_mapFromArray  ***END***");
    }

    @Test
    public void fromJsonLenientPhp_mapFromArray() throws Exception {
        System.out.println("fromJsonLenientPhp_mapFromArray  ***START***");

        // {} <- [], 此时：[] 视为 null
        // 此处与 GsonUtil 不一样，Gson 默认就允许从 [] 读取 Map
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"strMap\": []}");
            System.out.println("{} <- [] data.strMap:" + (data.strMap == null ? "null" : data.strMap));
            assertNull(data.strMap);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.strMap = null;
            assertEquals(expectedData, data);
        }
        System.out.println("{} <- [], 此时：[] 视为 null ***END***");

        // {} <- [["a","b"]]，Moshi 拒绝
        try {
            Data data = lenientPhpJsonAdapter.fromJson("{\"strMap\": [[\"a\",\"b\"]]}");
            System.out.println("{} <- [[\"a\",\"b\"]] data.strMap:" + (data.strMap == null ? "null" : data.strMap));
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected BEGIN_OBJECT but was BEGIN_ARRAY at path $.strMap", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("{} <- [[\"a\",\"b\"]]，Moshi 拒绝，不需要支持，不用容忍处理 ***END***");

        System.out.println("fromJsonLenientPhp_mapFromArray  ***END***");
    }

    @Test
    public void fromJson_objFromEmptyString() throws Exception {
        System.out.println("fromJson_objFromEmptyString  ***START***");

        // {} <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"data\": \"\"}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected BEGIN_OBJECT but was STRING at path $.data", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("{} <- \"\"，Moshi本身不支持 ***END***");

        // {} <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"data2\": \"\"}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected BEGIN_OBJECT but was STRING at path $.data2", e.getMessage());
            assertNull(e.getCause());
            e.printStackTrace();
        }
        System.out.println("{} <- \"\"，Moshi本身不支持 ***END***");

        System.out.println("fromJson_objFromEmptyString  ***END***");
    }

    @Test
    public void fromJsonLenientPhp_objFromEmptyString() throws Exception {
        System.out.println("fromJsonLenientPhp_objFromEmptyString  ***START***");

        // {} <- "", 此时："" 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"data\": \"\"}");
            System.out.println("{} <- \"\", 此时：\"\" 视为 null data.data:" + (data.data == null ? "null" : data.data));
            assertNull(data.data);
            Data expectedData = new Data();
            assertEquals(expectedData, data);
        }
        System.out.println("{} <- \"\", 此时：\"\" 视为 null ***END***");

        // {} <- "", 此时："" 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"data2\": \"\"}");
            System.out.println("{} <- \"\", 此时：\"\" 视为 null data.data2:" + (data.data2 == null ? "null" : data.data2));
            assertNull(data.data2);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.data2 = null;
            assertEquals(expectedData, data);
        }
        System.out.println("{} <- \"\", 此时：\"\" 视为 null ***END***");

        System.out.println("fromJsonLenientPhp_objFromEmptyString  ***END***");
    }

    @Test
    public void fromJson_objFromEmptyArray() throws Exception {
        // {} <- []
        try {
            Data data = jsonAdapter.fromJson("{\"data\": []}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected BEGIN_OBJECT but was BEGIN_ARRAY at path $.data", e.getMessage());
            assertNull(e.getCause());
        }

        // {} <- []
        try {
            Data data = jsonAdapter.fromJson("{\"data2\": []}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected BEGIN_OBJECT but was BEGIN_ARRAY at path $.data2", e.getMessage());
            assertNull(e.getCause());
        }
    }

    @Test
    public void fromJsonLenientPhp_objFromEmptyArray() throws Exception {
        // {} <- [], 此时：[] 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"data\": []}");
            assertNull(data.data);
            Data expectedData = new Data();
            assertEquals(expectedData, data);
        }

        // {} <- [], 此时：[] 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"data2\": []}");
            assertNull(data.data2);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.data2 = null;
            assertEquals(expectedData, data);
        }
    }

    @Test
    public void fromJsonLenientPhp_deepIn() throws Exception {
        // {} <- "", 此时："" 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"dataList\": [\"\"]}");
            assertNull(data.dataList.get(0));
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.dataList = Collections.singletonList(null);
            assertEquals(expectedData, data);
        }

        // int <- "", 此时："" 视为 null，仍然拒绝
        // 此处与 GsonUtil 不一样，Gson 会忽略此时的 null
        try {
            Data data = lenientPhpJsonAdapter.fromJson("{\"dataList\": [{\"num\": \"\"}]}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected an int but was  at path $.dataList[0].num", e.getMessage());
            assertNull(e.getCause());
        }

        // Integer <- "", 此时："" 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"dataList\": [{\"num2\": \"\"}]}");
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            Data2 data2 = new Data2();
            data2.num2 = null;
            expectedData.dataList = Collections.singletonList(data2);
            assertEquals(expectedData, data);
        }
    }

    @Test
    public void fromJson_objFromEmptyArrayAlias() throws Exception {
        // {} <- []，Moshi 拒绝
        try {
            Data data = jsonAdapter.fromJson("{\"a_data\": []}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected BEGIN_OBJECT but was BEGIN_ARRAY at path $.a_data", e.getMessage());
            assertNull(e.getCause());
        }

        // Integer <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"dataList\": [{\"a_num\": \"\"}]}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected an int but was  at path $.dataList[0].a_num", e.getMessage());
            assertNull(e.getCause());
        }

        // Integer <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"a_data\": {\"num2\": \"\"}}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected an int but was  at path $.a_data.num2", e.getMessage());
            assertNull(e.getCause());
        }

        // Integer <- ""
        try {
            Data data = jsonAdapter.fromJson("{\"a_data\": {\"a_num\": \"\"}}");
            fail("data: " + data);
        } catch (JsonDataException e) {
            assertEquals("Expected an int but was  at path $.a_data.a_num", e.getMessage());
            assertNull(e.getCause());
        }
    }

    @Test
    public void fromJsonLenientPhp_objFromEmptyArrayAlias() throws Exception {
        // {} <- [], 此时：[] 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"a_data\": []}");
            assertNull(data.dataAlias);
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.dataAlias = null;
            assertEquals(expectedData, data);
        }

        // Integer <- "", 此时："" 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"dataList\": [{\"a_num\": \"\"}]}");
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            Data2 data2 = new Data2();
            data2.numAlias = null;
            expectedData.dataList = Collections.singletonList(data2);
            assertEquals(expectedData, data);
        }

        // Integer <- "", 此时："" 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"a_data\": {\"num2\": \"\"}}");
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            Data2 data2 = new Data2();
            data2.num2 = null;
            expectedData.dataAlias = data2;
            assertEquals(expectedData, data);
        }

        // Integer <- "", 此时："" 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{\"a_data\": {\"a_num\": \"\"}}");
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            Data2 data2 = new Data2();
            data2.numAlias = null;
            expectedData.dataAlias = data2;
            assertEquals(expectedData, data);
        }
    }

    @Test
    public void fromJsonLenientPhp_multiTypeNotMatch() throws Exception {
        // Integer <- "", 此时："" 视为 null
        // Long <- "", 此时："" 视为 null
        // Float <- "", 此时："" 视为 null
        // Double <- "", 此时："" 视为 null
        // [] <- "", 此时："" 视为 []
        // {} <- [], 此时：[] 视为 null
        // {} <- "", 此时："" 视为 null
        {
            Data data = lenientPhpJsonAdapter.fromJson("{" +
                    "\"iNum2\": \"\", " +
                    "\"lNum2\": \"\", " +
                    "\"fNum2\": \"\", " +
                    "\"dNum2\": \"\", " +
                    "\"strList\": \"\", " +
                    "\"data2\": [], " +
                    "\"dataList\": [\"\", {\"num2\": \"\"}]" +
                    "}");
            Data expectedData = new Data();
            assertNotEquals(expectedData, data);
            expectedData.iNum2 = null;
            expectedData.lNum2 = null;
            expectedData.fNum2 = null;
            expectedData.dNum2 = null;
            expectedData.strList = Collections.emptyList();
            expectedData.data2 = null;
            Data2 data2 = new Data2();
            data2.num2 = null;
            List<Data2> dataList = new ArrayList<>();
            dataList.add(null);
            dataList.add(data2);
            expectedData.dataList = dataList;
            assertEquals(expectedData, data);
        }
    }

    @SuppressWarnings({"unused", "EqualsReplaceableByObjectsCall"})
    public static class Data {
        private int iNum = 9;
        private Integer iNum2 = 9;
        private long lNum = 9;
        private Long lNum2 = 9L;
        private float fNum = 9;
        private Float fNum2 = 9F;
        private double dNum = 9;
        private Double dNum2 = 9.0;
        private String[] strArr = new String[]{"v"};
        private List<String> strList = Collections.singletonList("v");
        private Map<String, String> strMap = Collections.singletonMap("k", "v");
        private Data data;
        @Json(name = "a_data")
        private Data2 dataAlias = new Data2();
        private Data2 data2 = new Data2();
        private List<Data2> dataList = Collections.singletonList(new Data2());

        public int getiNum() {
            return iNum;
        }

        public void setiNum(int iNum) {
            this.iNum = iNum;
        }

        public Integer getiNum2() {
            return iNum2;
        }

        public void setiNum2(Integer iNum2) {
            this.iNum2 = iNum2;
        }

        public long getlNum() {
            return lNum;
        }

        public void setlNum(long lNum) {
            this.lNum = lNum;
        }

        public Long getlNum2() {
            return lNum2;
        }

        public void setlNum2(Long lNum2) {
            this.lNum2 = lNum2;
        }

        public float getfNum() {
            return fNum;
        }

        public void setfNum(float fNum) {
            this.fNum = fNum;
        }

        public Float getfNum2() {
            return fNum2;
        }

        public void setfNum2(Float fNum2) {
            this.fNum2 = fNum2;
        }

        public double getdNum() {
            return dNum;
        }

        public void setdNum(double dNum) {
            this.dNum = dNum;
        }

        public Double getdNum2() {
            return dNum2;
        }

        public void setdNum2(Double dNum2) {
            this.dNum2 = dNum2;
        }

        public String[] getStrArr() {
            return strArr;
        }

        public void setStrArr(String[] strArr) {
            this.strArr = strArr;
        }

        public List<String> getStrList() {
            return strList;
        }

        public void setStrList(List<String> strList) {
            this.strList = strList;
        }

        public Map<String, String> getStrMap() {
            return strMap;
        }

        public void setStrMap(Map<String, String> strMap) {
            this.strMap = strMap;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public Data2 getDataAlias() {
            return dataAlias;
        }

        public void setDataAlias(Data2 dataAlias) {
            this.dataAlias = dataAlias;
        }

        public Data2 getData2() {
            return data2;
        }

        public void setData2(Data2 data2) {
            this.data2 = data2;
        }

        public List<Data2> getDataList() {
            return dataList;
        }

        public void setDataList(List<Data2> dataList) {
            this.dataList = dataList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Data data1 = (Data) o;

            if (iNum != data1.iNum) return false;
            if (lNum != data1.lNum) return false;
            if (Float.compare(data1.fNum, fNum) != 0) return false;
            if (Double.compare(data1.dNum, dNum) != 0) return false;
            if (iNum2 != null ? !iNum2.equals(data1.iNum2) : data1.iNum2 != null) return false;
            if (lNum2 != null ? !lNum2.equals(data1.lNum2) : data1.lNum2 != null) return false;
            if (fNum2 != null ? !fNum2.equals(data1.fNum2) : data1.fNum2 != null) return false;
            if (dNum2 != null ? !dNum2.equals(data1.dNum2) : data1.dNum2 != null) return false;
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            if (!Arrays.equals(strArr, data1.strArr)) return false;
            if (strList != null ? !strList.equals(data1.strList) : data1.strList != null)
                return false;
            if (strMap != null ? !strMap.equals(data1.strMap) : data1.strMap != null) return false;
            if (data != null ? !data.equals(data1.data) : data1.data != null) return false;
            if (dataAlias != null ? !dataAlias.equals(data1.dataAlias) : data1.dataAlias != null)
                return false;
            if (data2 != null ? !data2.equals(data1.data2) : data1.data2 != null) return false;
            return dataList != null ? dataList.equals(data1.dataList) : data1.dataList == null;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = iNum;
            result = 31 * result + (iNum2 != null ? iNum2.hashCode() : 0);
            result = 31 * result + (int) (lNum ^ (lNum >>> 32));
            result = 31 * result + (lNum2 != null ? lNum2.hashCode() : 0);
            result = 31 * result + (fNum != +0.0f ? Float.floatToIntBits(fNum) : 0);
            result = 31 * result + (fNum2 != null ? fNum2.hashCode() : 0);
            temp = Double.doubleToLongBits(dNum);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            result = 31 * result + (dNum2 != null ? dNum2.hashCode() : 0);
            result = 31 * result + Arrays.hashCode(strArr);
            result = 31 * result + (strList != null ? strList.hashCode() : 0);
            result = 31 * result + (strMap != null ? strMap.hashCode() : 0);
            result = 31 * result + (data != null ? data.hashCode() : 0);
            result = 31 * result + (dataAlias != null ? dataAlias.hashCode() : 0);
            result = 31 * result + (data2 != null ? data2.hashCode() : 0);
            result = 31 * result + (dataList != null ? dataList.hashCode() : 0);
            return result;
        }

        @NonNull
        @Override
        public String toString() {
            return "Data{" +
                    "iNum=" + iNum +
                    ", iNum2=" + iNum2 +
                    ", lNum=" + lNum +
                    ", lNum2=" + lNum2 +
                    ", fNum=" + fNum +
                    ", fNum2=" + fNum2 +
                    ", dNum=" + dNum +
                    ", dNum2=" + dNum2 +
                    ", strArr=" + Arrays.toString(strArr) +
                    ", strList=" + strList +
                    ", strMap=" + strMap +
                    ", data=" + data +
                    ", dataAlias=" + dataAlias +
                    ", data2=" + data2 +
                    ", dataList=" + dataList +
                    '}';
        }
    }

    @SuppressWarnings({"unused", "EqualsReplaceableByObjectsCall"})
    public static class Data2 {
        private int num = 3;
        private Integer num2 = 3;
        @Json(name = "a_num")
        private Integer numAlias = 3;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public Integer getNum2() {
            return num2;
        }

        public void setNum2(Integer num2) {
            this.num2 = num2;
        }

        public Integer getNumAlias() {
            return numAlias;
        }

        public void setNumAlias(Integer numAlias) {
            this.numAlias = numAlias;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Data2 data2 = (Data2) o;

            if (num != data2.num) return false;
            if (num2 != null ? !num2.equals(data2.num2) : data2.num2 != null) return false;
            return numAlias != null ? numAlias.equals(data2.numAlias) : data2.numAlias == null;
        }

        @Override
        public int hashCode() {
            int result = num;
            result = 31 * result + (num2 != null ? num2.hashCode() : 0);
            result = 31 * result + (numAlias != null ? numAlias.hashCode() : 0);
            return result;
        }

        @NonNull
        @Override
        public String toString() {
            return "Data2{" +
                    "num=" + num +
                    ", num2=" + num2 +
                    ", numAlias=" + numAlias +
                    '}';
        }
    }
}