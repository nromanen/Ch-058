<template>
  <div class="md-layout md-gutter">
    <div class="md-layout-item md-size-8"></div>
    <div id="parent" class="md-layout-item md-size-55 container">
      <div class="md-display-1">
        {{title}}
      </div>
      <div class="mytext md-content md-theme-default">
          <p v-html="text" style="white-space: pre-line;">{{text}}</p><br><br>

      </div>
      <div id="child" class="content">
        <a v-on:click="like">
          <img v-if="isLiked" src="../../assets/add active.png" v-my-tooltip.bottom-center="'Dont like'" height="32" width="32"/>
          <img v-if="!isLiked" src="../../assets/add.png" v-my-tooltip.bottom-center="'Like it!'" height="32" width="32"/>
        </a>
        {{countLike}}

        <a v-on:click="dislike()">
          <img v-if="!isUnliked" src="../../assets/minus.png" v-my-tooltip.bottom-center="'Dislike it!'" height="32" width="32"/>
          <img v-if="isUnliked" src="../../assets/minus active.png" v-my-tooltip.bottom-center="'Dont dislike'" height="32" width="32"/>
        </a>
        {{countDislike}}
      </div>
    </div>
    <div class="md-layout-item md-size-7"></div>
  </div>
</template>

<script src="./main.js"></script>

<style scoped src="./style.css"></style>

