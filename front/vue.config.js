module.exports = {  
    outputDir: "../src/main/resources/static",  
    indexPath: "../static/index.html",  
    devServer: {  
      proxy: "http://localhost:8080"  
    },
    pages: {
      index: {        
        entry: 'src/main.js',
        title: '통신판매사업자 통합검색서비스'        
      }
    },
    chainWebpack: config => {  
      const svgRule = config.module.rule("svg");    
      svgRule.uses.clear();    
      svgRule.use("vue-svg-loader").loader("vue-svg-loader");  
    }  
  };