open module COL {
    requires lombok;
    requires hutool.all;
    requires java.logging;
    requires com.almasb.fxgl.all;
    requires annotations;
    exports com.fengmaster.game.crossoneline;
    exports com.fengmaster.game.crossoneline.event;
    exports com.fengmaster.game.crossoneline.base;
}
