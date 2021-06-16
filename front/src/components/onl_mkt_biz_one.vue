<template>
  <div>
    <h1>{{ msg }}</h1>
    <b-table id="search_result" :items="search_result" :fields="fields" stacked small/>
    <b-button variant="outline-primary" @click="$router.back()">검색결과 화면으로 돌아가기</b-button>
  </div> 
</template>

<script scope>
import axios from 'axios'

//const API_URL = 'http://localhost:8080/';
const API_URL = 'http://ec2-3-37-127-73.ap-northeast-2.compute.amazonaws.com/';

export default {
     data() {
      return {
        msg : '통신판매사업자 통합검색서비스',
        search_result : '',
        fields:[
                {key:'bizregno', label:'사업자번호'},                
                {key:'bizname', label:'업체명'},
                {key:'crp_prv_cd', label:'개인/법인'},
                {key:'ceoname', label:'대표자'},
                {key:'tel', label:'전화번호'},
                {key:'email', label:'전자우편'},
                {key:'address', label:'사업장소재지'},
                {key:'address_road', label:'사업장소재지(도로명)'},
                {key:'regno', label:'통신판매번호'},
                {key:'regdate', label:'신고일자'},
                {key:'status', label:'업소상태'},
                {key:'orgname', label:'신고기관명'},
                {key:'orgtel', label:'신고기관연락처'},
                {key:'salestype', label:'판매방식'},
                {key:'product', label:'주요상품'},
                {key:'domain', label:'도메인주소'},
                {key:'hostlocation', label:'호스트서버소재지'}
        ]
      }
    },
    methods : {      
      select:function() {
        var bizregno = this.$route.params.bizregno;
        return axios
          .get(API_URL + 'biz/' + bizregno, {
          })
          .then(response => {
            console.log(response.data);
            this.search_result = response.data;
          }).catch((e) => {
            console.log(e);
        });
      }
    },
    beforeMount() {
      this.$destroy();
      this.select();
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
