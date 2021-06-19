<template>
  <div>
    <div style="margin-bottom: 10px;height:60px;">
      <h3 style="padding-top: 3px;text-align: left;padding-left: 10px">当前路径：</h3>
      <div style="padding-left: 10px">
        <div style="float:left;position: relative">
          <el-select v-model="value" placeholder="请选择" size="small" style="width: 240px;margin-right: 7px">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </div>
        <el-button circle style="float: left;padding:8px;">
          <img src="../assets/upperDir.svg" style="height: 15px;width: 15px"/>
        </el-button>
      </div>
    </div>
    <div style="margin-top: 40px">
      <h3 style="padding-top: 3px;text-align: left;padding-left: 10px">上传文件：</h3>
      <el-upload
          class="upload-demo"
          ref="upload"
          action="../"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :file-list="fileList"
          :limit="1"
          :auto-upload="false">
        <el-button slot="trigger" type="primary" size="small">选取文件</el-button>
        <el-button style="margin-left: 15px;" type="success" size="small" @click="submitUpload">确认上传</el-button>
        <div slot="tip" class="el-upload__tip">请导入.json或.xml格式的图文件</div>
      </el-upload>
    </div>
  </div>
</template>

<script>
export default {
  name: "KGUpload",
  data() {
    return {
      fileList: [],
      value: "",
      options: []
    };
  },
  methods: {
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    }
  },
}
</script>

<style scoped>
</style>
