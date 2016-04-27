package com.qihuan.easydialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.qihuan.adapter.EDSimpleAdapter;
import com.qihuan.adapter.ViewHolder;
import com.qihuan.core.EasyButton;
import com.qihuan.core.EasyDialog;
import com.qihuan.core.EasyUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SampleActivity extends AppCompatActivity {

    private ListView mlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        initView();
        initListener();
    }


    private void initView() {
        mlistView = (ListView) findViewById(R.id.act_sample_list);
        List<String> items = Arrays.asList(
                "普通dialog",//0
                "无标题",//1
                "仅确认",//2
                "仅取消",//3
                "仅neutral",//4
                "neutral+确认",//5
                "确认换颜色",//6
                "title换颜色+左对齐布局",//7
                "content换颜色+左对齐布局",//8
                "title富文本",//9
                "content富文本",//10
                "不自动消失",//11
                "超长文本",//12
                "列表",//13
                "自定义列表adapter",//14
                "列表样式调整",//15
                "自定义view",//16
                "普通进度条",//17
                "横向进度条",//18
                "横向进度条带进度提示"//19
        );
        mlistView.setAdapter(new EDSimpleAdapter<String>(items, R.layout.item_sample) {
            @Override
            protected void bindView(View convertView, int position, ViewHolder viewHolder) {
                String item = getItem(position);
                viewHolder.getTextView(R.id.item_sample_text).setText(item);
            }
        });


    }

    public void showToast(String msg) {
        Toast.makeText(SampleActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void initListener() {
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EasyDialog.Builder builder = new EasyDialog.Builder(SampleActivity.this);
                switch (position) {
                    case 0:
                        builder.title("设计带给你好心情")
                                .content("希望能用设计给人带来好的心情。\n谢谢大家观赏。")
                                .positiveText("确认")
                                .onPositive(new EasyDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull EasyDialog dialog, @NonNull EasyButton.EasyButtonType which) {
                                        showToast("点击确认");
                                    }
                                })
                                .negativeText("取消")
                                .onNegative(new EasyDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull EasyDialog dialog, @NonNull EasyButton.EasyButtonType which) {
                                        showToast("点击取消");
                                    }
                                });
                        break;
                    case 1:
                        builder.content("希望能用设计给人带来好的心情。\n谢谢大家观赏。")
                                .positiveText("确认")
                                .negativeText("取消");
                        break;
                    case 2:
                        builder.content("希望能用设计给人带来好的心情。\n谢谢大家观赏。")
                                .positiveText("确认");
                        break;
                    case 3:
                        builder.content("希望能用设计给人带来好的心情。\n谢谢大家观赏。")
                                .negativeText("取消");
                        break;
                    case 4:
                        builder.content("希望能用设计给人带来好的心情。\n谢谢大家观赏。")
                                .neutralText("更多");
                        break;
                    case 5:
                        builder.content("希望能用设计给人带来好的心情。\n谢谢大家观赏。")
                                .positiveText("确认")
                                .neutralText("更多");
                        break;
                    case 6:
                        builder.content("希望能用设计给人带来好的心情。\n谢谢大家观赏。")
                                .positiveText("确认")
                                .positiveColorRes(R.color.font_danger)
                                .negativeText("取消");
                        break;

                    case 7:
                        builder.title("设计带给你好心情")
                                .titleColorRes(R.color.font_danger)
                                .titleGravity(Gravity.LEFT)
                                .content("希望能用设计给人带来好的心情。\n谢谢大家观赏。")
                                .positiveText("确认")
                                .negativeText("取消");
                        break;
                    case 8:
                        builder.title("设计带给你好心情")
                                .content("希望能用设计给人带来好的心情。\n谢谢大家观赏。")
                                .contentColorRes(R.color.font_danger)
                                .contentGravity(Gravity.LEFT)
                                .positiveText("确认")
                                .negativeText("取消");
                        break;

                    case 9:
                        builder.title(Html.fromHtml("设计带给你<font color='#147ed4'><i>好心情</i></font>"))
                                .content("希望能用设计给人带来好的心情。\n谢谢大家观赏。")
                                .positiveText("确认")
                                .negativeText("取消");
                        break;

                    case 10:
                        builder.title("设计带给你好心情")
                                .content(Html.fromHtml("希望能用设计给人带来好的心情。<br/><font color='#147ed4'><i>谢谢大家观赏。</i></font>"))
                                .positiveText("确认")
                                .negativeText("取消");
                        break;

                    case 11:
                        builder.title("设计带给你好心情")
                                .content("希望能用设计给人带来好的心情。\n谢谢大家观赏。")
                                .positiveText("确认")
                                .negativeText("取消")
                                .autoDismiss(false);
                        break;

                    case 12:
                        builder.title("设计带给你好心情")
                                .content("《玄界之门》是起点中文网首发的一部奇幻修真小说，作者是起点白金作家忘语。\n石牧：本书男主，东洲大陆齐国越州人氏，从小生活在渔村，皮肤微微黑红，浓眉大眼。为人意志坚定，有情有义，处事从容、不卑不亢，刚强不屈。受异血附体身体产生异变\n天阴姹女：万珑山天阴宗女弟子，相救石牧被其表白，允诺他三十岁前进阶先天武者便告之本名[2] \n香珠：天蚌灵女，天生的水行术士。将异血污秽了伴生灵珠赠予救命恩人石牧，海族圣女，在勇士之门见到石牧，成为灵阶术士，在勇士之门又被石牧救了一次。\n钟秀：钟家之后受钟明所托遗孤，额头上有块青色胎记破坏了俏丽的容颜，曾经和石牧一起被金家和吴家联手追杀，三品凤音血脉，现被带往妙音宗，胎记消失，地位很高，打算进入碧波池突破先天瓶颈。[3] \n烟罗：石牧灵宠，死灵界面骷髅，能说话，实力已经进阶先天。\n珂儿：妙音宗的木属性术士学徒，天赋很高，善长木属性治愈类的术法，与石牧的关系不错。\n金小钗：炎国黑魔门地阶大长老弟子。化名韩湘绣潜伏在黑魔门劫持玄武宗的新弟子中，认识了石牧，后用黑魔蛇偷袭玄武宗风行星阶术士余千机。蛮族入侵因天阴姹女的缘故将石牧调到联盟的一处制符据点“鼠巢”，称大长老为祖爷爷，作为领头人准备带领石牧等四人参加两月之后的魔阳大典。\n")
                                .contentGravity(Gravity.LEFT)
                                .positiveText("确认")
                                .negativeText("取消");
                        break;


                    case 13:
                        builder.title("设计带给你好心情")
                                .items("哈哈", "嘿嘿", Html.fromHtml("<font color='#147ed4'>呵呵</font>"))
                                .itemsCallback(new EasyDialog.ListCallback<CharSequence>() {
                                    @Override
                                    public void onItemClick(@NonNull EasyDialog dialog, @NonNull View view, @NonNull int position, @NonNull CharSequence item) {
                                        showToast((String) item);
                                    }
                                })
                                .positiveText("确认")
                                .negativeText("取消");
                        break;

                    case 14:
                        List<CharSequence> items = Arrays.asList("哈哈", "嘿嘿", Html.fromHtml("<font color='#147ed4'>呵呵</font>"));
                        builder.title("设计带给你好心情")
                                .adapterSimple(new EDSimpleAdapter<CharSequence>(items, R.layout.ed_list_item) {
                                    @Override
                                    protected void bindView(View convertView, int position, ViewHolder viewHolder) {
                                        viewHolder.getTextView(R.id.ed_item_text).setText(getItem(position));
                                    }
                                }, new EasyDialog.ListCallback<CharSequence>() {
                                    @Override
                                    public void onItemClick(@NonNull EasyDialog dialog, @NonNull View view, @NonNull int position, @NonNull CharSequence item) {
                                        showToast(item.toString());
                                    }
                                })
                                .positiveText("确认")
                                .negativeText("取消");
                        break;

                    case 15:
                        builder.items("哈哈", "嘿嘿", Html.fromHtml("<font color='#147ed4'>呵呵</font>"))
                                .itemsColorRes(R.color.font_danger)
                                .itemsGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL)
                                .itemsHeight(EasyUtil.dp2px(80))
                                .itemsTextSize(20F);
                        break;
                    case 16:
                        builder.title("自定义view")
                                .customView(R.layout.custom_view, true)
                                .positiveText("确认");
                        break;
                    case 17:
                        builder.title("处理中")
                                .progress(true, 0)
                                .negativeText("取消")
                                .positiveText("确认");
                        break;
                    case 18:
                        builder.title("处理中")
                                .progress(false, 20)
                                .negativeText("取消")
                                .positiveText("确认");
                        break;
                    case 19:
                        builder.title("处理中")
                                .progress(false, 20, true)
                                .negativeText("取消")
                                .positiveText("确认");
                        break;
                }

                final EasyDialog easyDialog = builder.show();


                if (position == 18 || position == 19) {
                    //补齐进度
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (easyDialog.getCurrentProgress() < easyDialog.getMaxProgress()) {
                                easyDialog.incrementProgress(1);
                                try {
                                    TimeUnit.MILLISECONDS.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
            }
        });

    }
}
