<template>
  <div class="md-layout md-gutter">
    <div id="parent" class="md-layout-item md-size-75 container">
      <div class="md-headline">
        {{title}}
      </div>
      <div class="mytext">
        <p v-html="text" style="white-space: pre-line;">{{text}}</p><br>
      </div>
      <div id="child" class="content">
        <a v-on:click="like">
          <img v-if="isLiked" src="../../assets/add active.png" height="32" width="32"/>
          <img v-if="!isLiked" src="../../assets/add.png" height="32" width="32"/>
        </a>
        {{countLike}}

        <a v-on:click="dislike()">
          <img v-if="!isUnliked" src="../../assets/minus.png" height="32" width="32"/>
          <img v-if="isUnliked" src="../../assets/minus active.png" height="32" width="32"/>
        </a>
        {{countDislike}}
      </div>
    </div>

    <div class="md-layout-item md-size-25">
      <gmap-map
        :center="center"
        :zoom="5"
        style="width: 100%; height: 300px"
      >
        <gmap-marker
          :key="index"
          v-for="(m, index) in markers"
          :position="m.position"
          :clickable="true"
          @click="center=m.position"
        ></gmap-marker>
      </gmap-map>
    </div>
  </div>
</template>

<script src="./main.js"></script>

<style scoped src="./style.css"></style>

