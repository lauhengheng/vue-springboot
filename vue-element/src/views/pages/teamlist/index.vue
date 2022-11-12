<template>
    <div>
    <!--头部-->
    <top></top>
    <!--导航-->
    <navs></navs>
    <!--核心内容start-->
    <section class="heading-page header-text" id="top">
        <div class="container">
          <div class="row">
            <div class="col-lg-12">
              <h6>获取所有详细信息</h6>
              <h2>在线教学工具</h2>
            </div>
          </div>
        </div>
      </section>
      
    <section class="meetings-page" id="meetings">
    <div class="container">
      <div class="row">
        <div class="col-lg-12">
          <div class="row">
            <div class="col-lg-12">
              <div class="row grid">

                <div class="col-lg-4 templatemo-item-col all soon" v-for="team in teamList">
                  <div class="meeting-item">
                    <div class="thumb">
                      <div class="price">
                  
                      </div>
                      <!-- <a href="#/teaminfo"><img :src="team.images" width="200px" height="200px" alt=""></a> -->
                      <router-link :to="{path:'/teaminfo',query:{teamId:team.id}}"><img :src="require('@/assets/images/'+team.images)" width="200px" height="200px" alt=""></router-link>
                    </div>
                    <div class="down-content">
                      <div class="date">
                        <h6>Nov </h6>
                      </div>
                      <h4>{{team.name}}</h4>
                      <p>社长:{{team.userInfo.username}}</p>
                    </div>
                  </div>
                </div>

              </div>
            </div>
            <div class="col-lg-12">
              <div class="pagination">
                
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
     <!--底部-->
     <foot></foot>
  </section>

    <!--核心内容end-->

   
    </div>

</template>

<script>
    import top from '../../qdutils/top/index'
    import navs from '../../qdutils/navs/index'
    import foot from '../../qdutils/footer/index'
// import { request } from 'http'
    import service from '@/utils/request'
    export default {
      name: "index",
      components:{top,navs,foot},
      //初始数据
      data(){
        return{
          teamList:[]
        }
      },

      //获取数据信息
      created(){
        this.getList()
      },
      //方法列表
      methods:{
        //获取方法
        getList(){
          service({
            url:'team/queryTeamAll',
            method:'get',
            params:''
          }).then(res=>{
            this.teamList=res.data
          })
        }
      }
    }
</script>

<style scoped>
    @import "../../../vendor/bootstrap/css/bootstrap.min.css";
    @import "../../../assets/css/templatemo-edu-meeting.css";
</style>