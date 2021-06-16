import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'

import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(VueRouter)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.config.productionTip = false

import OnlMktBiz from './components/onl_mkt_biz.vue'
import OnlMktBizOne from './components/onl_mkt_biz_one.vue'


const router = new VueRouter({
  routes:[
    { path:'/', component:OnlMktBiz },
    { path:'/biz/:bizregno', component:OnlMktBizOne, props:true }
  ]
});

new Vue({
  render: h => h(App),
  router : router
}).$mount('#app')
