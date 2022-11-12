<template>
<div>
    <header class="header-area header-sticky">
      <div class="container">
          <div class="row">
              <div class="col-12">
                  <nav class="main-nav">
                      <!-- ***** Logo Start ***** -->
                      <a href="index.html" class="logo">
                          大学社团
                      </a>
                      <!-- ***** Logo End ***** -->
                      <!-- ***** Menu Start ***** -->
                      <ul class="nav">
                          <li class="scroll-to-section"><a href="#/indexs">主页</a></li>
                          <li><a href="#/teamlist">社团列表</a></li>
                          <li class="scroll-to-section"><a href="#/myteam">我的社团</a></li>
                          <li><a href="#/leave">在线留言</a></li>
                  <li class="scroll-to-section" v-if="type!=2"><a href="#/">后台管理</a></li>
                      </ul>        
                      <a class='menu-trigger'>
                          <span>Menu</span>
                      </a>
                      <!-- ***** Menu End ***** -->
                  </nav>
              </div>
          </div>
      </div>
  </header>
</div>

</template>

<script>
  export default{
    name:"index",
    data(){
        return{
            type:localStorage.getItem("type")
        }
    }
  }
</script>

<style scoped>
</style>