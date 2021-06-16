<template>
  <div>
    <h1>{{ msg }}</h1>
      <b-form method="get" v-on:submit.prevent="search">
        <b-input-group class="mt-2">
          <b-form-input v-model="text" placeholder="검색어를 입력하세요" v-on:keydown.enter.prevent="search" autofocus></b-form-input>
          <b-button variant="outline-primary" type="submit" >검색</b-button>      
        </b-input-group>  
      </b-form>
      <b-row class="mt-3">
        <b-col>
            <div v-if="search_result.result">
              <h6>검색결과 제공 {{search_result.hits}} 건, 전체 {{search_result.totalhits}} 건 ({{search_result.took}}ms)</h6>
                <b-table id="search_result" :items="search_result.items" :fields="fields"
                          :per-page="perPage" :current-page="currentPage" responsive small>
                  <template #cell(bizregno)="data">
                      <b-link :to="{path:'/biz/'+data.item.bizregno}" v-html="data.item.bizregno"></b-link>
                  </template>
                </b-table>
                <b-pagination v-model="currentPage" :total-rows="rows" :per-page="perPage" aria-controls="search_result" align="center"/>
                
            </div>
            <div v-else>{{search_result_msg}}</div>
        </b-col>
    </b-row>
  </div> 
</template>

<script>
import axios from 'axios'

//const API_URL = 'http://localhost:8080/';
const API_URL = 'http://ec2-3-37-127-73.ap-northeast-2.compute.amazonaws.com/';

export default {
     data() {
      return {
        msg : '통신판매사업자 통합검색서비스',
        text: '',
        modalShow: false,
        search_result:{result:false, hits:0, totalhits:0, items:[], took:0},
        search_result_msg : '',
        currentPage:1,
        perPage:10,
        fields:[
                {key:'bizregno', label:'사업자번호'},                
                {key:'bizname', label:'업체명'},
                {key:'crp_prv_cd', label:'개인/법인'},
                {key:'ceoname', label:'대표자'},
                {key:'orgname', label:'신고기관명'},
                {key:'status', label:'업소상태'},
                {key:'product', label:'주요상품'},
                {key:'domain', label:'도메인주소'}
        ]
      }
    },
    methods : {      
      search:function() {
        if(this.isMobile()){
          this.fields = [
                {key:'bizregno', label:'사업자번호'},                
                {key:'bizname', label:'업체명'},
                {key:'ceoname', label:'대표자'},
                {key:'orgname', label:'신고기관명'}
          ];
        }else{          
          this.fields = [
                {key:'bizregno', label:'사업자번호'},                
                {key:'bizname', label:'업체명'},
                {key:'crp_prv_cd', label:'개인/법인'},
                {key:'ceoname', label:'대표자'},
                {key:'orgname', label:'신고기관명'},
                {key:'status', label:'업소상태'},
                {key:'product', label:'주요상품'},
                {key:'domain', label:'도메인주소'}
          ];
        }

        return axios
          .get(API_URL + 'search', {
            params : {
              text : this.text            
            }
          })
          .then(response => {
            this.search_result = response.data;
            console.log(this.search_result);
            if(this.search_result.hits == 0){
              this.search_result_msg = '검색 결과가 없습니다';
            }else{
              this.search_result.result = true;
            }          
          }).catch((e) => {
            console.log(e);
        });
      },
      isMobile:function(){
        return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);
      }
    },
    computed: {
      rows() {
        if(this.search_result != undefined) {
            return this.search_result.hits;
        } else {
            return 0;
        }
      }
    }
};

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
