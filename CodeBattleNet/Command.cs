/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2020 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
﻿using System.Collections;

namespace TetrisClient
{
    public class Command
    {
        public enum Rotation
        {
            ClockWize90,
            ClockWize180,
            ClockWize270,
            Suicide
        }

        public readonly Direction? _direction;
        public readonly Rotation? _rotation;
        public readonly Command _next;

        private Command(Direction direction)
        {
            _direction = direction;
            _rotation = null;
        }

        private Command(Rotation rotation)
        {
            _direction = null;
            _rotation = rotation;
        }

        private Command(Command copy, Command combineWith)
        {
            this._direction = copy._direction;
            this._rotation = copy._rotation;
            this._next = combineWith;
        }

        public Command Combine(Command combineWith)
        {
            if(_next == null) return new Command(this, combineWith);
            
            var current = this;
            var chain = new Stack();

            while (current != null)
            {
                chain.Push(current);
                current = current._next;
            }

            var result = new Command((Command)chain.Pop(), combineWith);
            while (chain.Count > 0)
            {
                result = new Command((Command)chain.Pop(), result);
            }

            return result;
        }

        public Command Combine(Direction direction)
        {
            return Combine(new Command(direction));
        }

        public override string ToString()
        {

            var result = "";
            if (_direction != null) result = _direction.ToString();
            else if (_rotation != null) result = string.Format("{0}{1}", Direction.Act.ToString(), formatRotationAction(_rotation.Value));
            if (_next != null) result += ", " + _next.ToString();

            return result;
        }

        private string formatRotationAction(Rotation rotation)
        {
            switch (rotation)
            {
                case Rotation.ClockWize180: return "(2)";
                case Rotation.ClockWize270: return "(3)";
                case Rotation.Suicide: return "(1, 1)";
                default: return "";
            }
        }

        public static Command MoveTo(Direction direction)
        {
            return new Command(direction);
        }

        public static Command Rotate90()
        {
            return new Command(Rotation.ClockWize90);
        }
        public static Command Rotate180()
        {
            return new Command(Rotation.ClockWize180);
        }

        public static Command Rotate270()
        {
            return new Command(Rotation.ClockWize270);
        }

        public static Command Suicide()
        {
            return new Command(Rotation.Suicide);
        }
    }
}
