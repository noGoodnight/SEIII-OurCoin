<template>
  <div v-show="editableTabs.length !== 0">
    <el-tabs style="height: 660px" v-model="editableTabsValue" type="card" editable @edit="handleTabsEdit">
      <el-tab-pane :key="item.id" v-for="(item, index) in editableTabs" :label="item.title" :name="item.id">
        <node-editor :prefix="item.id"></node-editor>
        <kg-d3 :refs="item.id+'KGD3'" suffix="item.id"
               @handleNodeEdit="handleNodeEdit"></kg-d3>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import KGd3 from "@/components/KGd3";
import NodeEditor from "@/components/NodeEditor";

export default {
  name: "KGTab",
  components: {
    "kg-d3": KGd3,
    "node-editor": NodeEditor,
  },
  data() {
    return {
      editableTabsValue: '1',
      editableTabs: [{
        title: 'Tab 1',
        id: '1',
      }],
      tabIndex: 1,
    }
  },
  methods: {
    handleTabsEdit(targetName, action) {
      if (action === 'add') {
        let newTabName = ++this.tabIndex + '';
        this.editableTabs.push({
          title: 'New Tab',
          name: newTabName,
          content: 'New Tab content'
        });
        this.editableTabsValue = newTabName;
      }
      if (action === 'remove') {
        let tabs = this.editableTabs;
        let activeName = this.editableTabsValue;
        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            if (tab.id === targetName) {
              let nextTab = tabs[index + 1] || tabs[index - 1];
              if (nextTab) {
                activeName = nextTab.id;
              }
            }
          });
        }
        this.editableTabsValue = activeName;
        this.editableTabs = tabs.filter(tab => tab.id !== targetName);
      }
    },
    handleNodeEdit() {
      console.log("info")
    },

  }
}
</script>

<style scoped>

</style>
