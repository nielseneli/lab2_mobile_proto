// Generated code from Butter Knife. Do not modify!
package erica.locationscrapbook;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MapsActivity_ViewBinding<T extends MapsActivity> implements Unbinder {
  protected T target;

  @UiThread
  public MapsActivity_ViewBinding(T target, View source) {
    this.target = target;

    target.addCurrent = Utils.findRequiredViewAsType(source, R.id.addCurrent, "field 'addCurrent'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.addCurrent = null;

    this.target = null;
  }
}
