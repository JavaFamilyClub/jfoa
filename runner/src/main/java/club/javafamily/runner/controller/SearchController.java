/*
 * Copyright (c) 2020, JavaFamily Technology Corp, All Rights Reserved.
 *
 * The software and information contained herein are copyrighted and
 * proprietary to JavaFamily Technology Corp. This software is furnished
 * pursuant to a written license agreement and may be used, copied,
 * transmitted, and stored only in accordance with the terms of such
 * license and with the inclusion of the above copyright notice. Please
 * refer to the file "COPYRIGHT" for further copyright and licensing
 * information. This software and information or any other copies
 * thereof may not be provided or otherwise made available to any other
 * person.
 */

package club.javafamily.runner.controller;

import club.javafamily.runner.controller.model.*;
import club.javafamily.runner.util.SecurityUtil;
import club.javafamily.runner.util.Tool;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(SecurityUtil.API_VERSION)
@Api("EM searcher")
public class SearchController {

   @PostConstruct
   public void createDocument() throws IOException {
      File searchLib = new File(Tool.getCacheDir(), "searchLib/");

      if(!searchLib.exists() || !searchLib.isDirectory()) {
         if(searchLib.mkdirs()) {
            LOGGER.info("Auto create dir: " + searchLib.getAbsolutePath());
         }
      }

      ObjectMapper mapper = new ObjectMapper();
      URL json = this.getClass().getResource("/config/admin/search-index.json");
      SearchModel searchModel = mapper.readValue(json, SearchModel.class);

      if(searchModel == null) {
         LOGGER.error("Can't found file: {}.", json.getPath());
         return;
      }

      Directory directory = new MMapDirectory(Paths.get(searchLib.toURI()));
      IndexWriterConfig writerConfig = new IndexWriterConfig(getAnalyzer());
      Searchable[] entries = searchModel.getEntries();

      try(IndexWriter writer = new IndexWriter(directory, writerConfig)) {
         for(Searchable searchable : entries) {
            String titleStr = searchable.getTitle();
            String routeStr = searchable.getRoute();
            String[] keywordsArray = searchable.getKeywords();

            TextField title = new TextField("title", titleStr, Field.Store.YES);
            StringField route = new StringField("route", routeStr, Field.Store.YES);
            TextField keyword
               = new TextField("keyword", String.join(" ", keywordsArray), Field.Store.NO);

            Document document = new Document();
            document.add(title);
            document.add(route);
            document.add(keyword);
            writer.addDocument(document);
         }
      }

      reader = DirectoryReader.open(directory);
      searcher = new IndexSearcher(reader);
      queryParser = new QueryParser("keyword", getAnalyzer());
   }

   @PreDestroy
   public void destroy() throws Exception {
      if(reader != null) {
         reader.close();
         reader = null;
         LOGGER.info("Close Search Index Reader!");
      }

      searcher = null;
      queryParser = null;
   }

   private Analyzer getAnalyzer() {
      return new StandardAnalyzer();
   }

   @GetMapping("/public/tool/search")
   @ApiOperation(value = "Search", httpMethod = "GET")
   public SearchResult search(@ApiParam("Search key words") @RequestParam("searchWords") String searchWords)
      throws Exception
   {
      Query query = queryParser.parse(searchWords);
      TopDocs topDocs = searcher.search(query, 10);

      List<Searchable> searchables = new ArrayList<>();
      SearchResult searchResult = new SearchResult(searchables);
      searchResult.setTotal(topDocs.totalHits);

      ScoreDoc[] scoreDocs = topDocs.scoreDocs;

      if(ArrayUtils.isEmpty(scoreDocs)) {
         return searchResult;
      }

      for(ScoreDoc doc : scoreDocs) {
         Searchable searchable = new Searchable();
         int docId = doc.doc;
         Document document = searcher.doc(docId);
         String route = document.get("route");
         String title = document.get("title");
         searchable.setTitle(title);
         searchable.setRoute(route);

         searchables.add(searchable);
      }

      return searchResult;
   }

   private IndexReader reader;
   private IndexSearcher searcher;
   private QueryParser queryParser;

   private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);
}
